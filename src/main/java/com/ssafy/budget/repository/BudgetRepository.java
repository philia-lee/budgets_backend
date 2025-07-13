package com.ssafy.budget.repository;

import com.ssafy.budget.entity.Budget;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BudgetRepository {

    // 특정 유저의 모든 예산 목록 조회
    @Select("""
        SELECT id, user_id, category_id, amount, start_date, end_date
        FROM budgets
        WHERE user_id = #{userId}
    """)
    List<Budget> findByUserId(Long userId);

    // 예산과 user가 맞는지 검증
    @Select("""
        SELECT COUNT(*) > 0
        FROM budgets
        WHERE id = #{budgetId} AND user_id = #{userId}
    """)
    boolean existsByIdAndUserId(@Param("userId") Long userId, @Param("budgetId") Long budgetId);

    // 예산 생성 (동일 user, 카테고리, 기간 중복 방지)
    @Insert("""
        INSERT INTO budgets
        (user_id, category_id, amount, start_date, end_date)
        VALUES
        (#{budget.user_id}, #{budget.category_id}, #{budget.amount}, #{budget.start_date}, #{budget.end_date})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(@Param("budget") Budget budget);

    // 예산 삭제
    @Delete("""
        DELETE FROM budgets
        WHERE user_id = #{userId} AND id = #{budgetId}
    """)
    void delete(@Param("userId") Long userId, @Param("budgetId") Long budgetId);

    // 예산 수정
    @Update("""
        UPDATE budgets
        SET
            category_id = #{budget.category_id},
            amount = #{budget.amount},
            start_date = #{budget.start_date},
            end_date = #{budget.end_date}
        WHERE id = #{budgetId} AND user_id = #{userId}
    """)
    void update(@Param("budget") Budget budget, @Param("budgetId") Long budgetId, @Param("userId") Long userId);

    // [중복 방지] 이번 달 동일 카테고리 예산 존재 여부
    @Select("""
        SELECT COUNT(*) > 0
        FROM budgets
        WHERE user_id = #{userId}
          AND category_id = #{categoryId}
          AND start_date = #{startDate}
          AND end_date = #{endDate}
    """)
    boolean existsByUserIdAndCategoryIdAndPeriod(@Param("userId") Long userId,
                                                 @Param("categoryId") Integer categoryId,
                                                 @Param("startDate") String startDate,
                                                 @Param("endDate") String endDate);

    // [예산 상세/알림용] 특정 user, 카테고리, 날짜가 포함된 예산 찾기
    @Select("""
        SELECT id, user_id, category_id, amount, start_date, end_date
        FROM budgets
        WHERE user_id = #{userId}
          AND category_id = #{categoryId}
          AND start_date <= #{date}
          AND end_date >= #{date}
        LIMIT 1
    """)
    Budget findBudgetByUserIdAndCategoryAndDate(@Param("userId") Long userId,
                                                @Param("categoryId") Integer categoryId,
                                                @Param("date") java.time.LocalDate date);
}
