package com.ssafy.transaction.repository;

import java.time.LocalDate; // LocalDate는 Date와 다릅니다. 필요에 따라 둘 다 유지하거나 하나만 사용하세요.
import java.util.Date; // Date 타입이 아직 필요하다면 유지
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ssafy.budget.entity.Budget; // 이 Budget 엔티티가 사용되지 않으면 삭제하는 것이 좋습니다.
import com.ssafy.transaction.dto.CreateTransactionRequest; // 필요하다면 이 DTO를 서비스에서 Transaction 엔티티로 변환
import com.ssafy.transaction.dto.TransactionResponse;
import com.ssafy.transaction.dto.UpdateTransaction;
import com.ssafy.transaction.entity.Transaction;

@Mapper
public interface TransactionRepository {

    @Insert("""
            INSERT INTO transactions
            (user_id, type, amount, category_id, description, date, created_at)
            VALUES
            (
                #{userId},
                #{transaction.type},
                #{transaction.amount},
                (
                    SELECT id
                    FROM categories
                    WHERE name = #{transaction.category} AND (user_id = #{userId} OR user_id IS NULL)
                ),
                #{transaction.description},
                #{transaction.date},
                NOW()
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "transaction.id")
    void save(@Param("userId") Long userId,
              @Param("transaction") CreateTransactionRequest transaction); // CreateTransactionRequest 유지

    @Select("SELECT count(*) FROM transactions WHERE id = #{id} AND user_id=#{userId}") // && 대신 AND 사용
    Boolean existsByIdAndUserId(@Param("userId") Long userId,@Param("id") Long id);

    @Update("""
             UPDATE transactions
            SET
                category_id = (
                    SELECT id
                    FROM categories
                    WHERE name = #{transaction.category} AND (user_id = #{userId} OR user_id IS NULL)
                ),
                type = #{transaction.type},
                amount = #{transaction.amount},
                description = #{transaction.description},
                date = #{transaction.date}
            WHERE id = #{id} AND user_id = #{userId}
            """)
    void update(@Param("userId") Long userId, @Param("id") Long id, @Param("transaction") UpdateTransaction transaction);

    @Select("""
            SELECT
            t.id,
            t.user_id AS userId, /* 별칭 추가 */
            t.type,
            t.amount,
            t.category_id, /* category_id 포함 */
            c.name AS category,
            t.description,
            t.date
        FROM
            transactions t
        JOIN
            categories c ON t.category_id = c.id
        WHERE
            t.user_id = #{userId} AND t.id = #{id} /* && 대신 AND 사용 */
        """)
    TransactionResponse show(@Param("userId") Long userId,@Param("id") int id);

    @Delete("DELETE FROM transactions WHERE id = #{id} AND user_id = #{userId}")
    void delete(@Param("userId") Long userId, @Param("id") Long id);

    // ✅ 이번 달 지출 합계 계산 메서드 (충돌 브랜치에서 추가된 부분)
    @Select("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE user_id = #{userId}
          AND category_id = #{categoryId}
          AND type = 'EXPENSE'
          AND date BETWEEN #{startDate} AND #{endDate}
        """)
    Integer getTotalSpentForCategory(@Param("userId") Long userId,
                                     @Param("categoryId") Integer categoryId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Select("""
            SELECT
                t.id,
                t.user_id AS userId, /* 별칭 추가 */
                t.type,
                t.amount,
                t.category_id, /* category_id 포함 */
                c.name AS category,
                t.description,
                t.date
            FROM
                transactions t
            JOIN
                categories c ON t.category_id = c.id
            WHERE
                t.user_id = #{userId}
            """)
    List<TransactionResponse> allshow(@Param("userId") Long userId); // Long userId만 받도록 수정
}