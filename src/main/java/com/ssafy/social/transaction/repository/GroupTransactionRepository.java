package com.ssafy.social.transaction.repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ssafy.social.transaction.dto.internal.UserSpending;
import com.ssafy.social.transaction.entity.GroupTransaction;

@Mapper
public interface GroupTransactionRepository {

	@Insert("""
			    INSERT INTO group_transactions (group_id, user_id, type, amount, category_id, description, date)
			    VALUES (#{groupId}, #{userId}, #{type}, #{amount}, #{categoryId}, #{description}, now())
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertGroupTransaction(GroupTransaction transaction);

	// 특정 그룹의 전체 거래 내역 조회
	@Select("""
			    SELECT id, group_id, user_id, type, amount, category_id, description, date
			    FROM group_transactions
			    WHERE group_id = #{groupId}
			""")
	List<GroupTransaction> findByGroupId(@Param("groupId") int groupId);

	// 특정 사용자+그룹의 거래 내역 조회
	@Select("""
			    SELECT * FROM group_transactions
			    WHERE group_id = #{groupId} AND user_id = #{userId}
			    ORDER BY date DESC
			""")
	List<GroupTransaction> findByGroupAndUser(@Param("groupId") int groupId, @Param("userId") Long userId);

	// 특정 날짜 범위 조회
	@Select("""
			    SELECT * FROM group_transactions
			    WHERE group_id = #{groupId}
			    AND date BETWEEN #{startDate} AND #{endDate}
			    ORDER BY date DESC
			""")
	List<GroupTransaction> findByDateRange(@Param("groupId") int groupId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Select("""
			    SELECT
			        COALESCE(SUM(amount), 0) AS totalExpense,
			        COALESCE(SUM(CASE WHEN user_id = #{userId} THEN amount ELSE 0 END), 0) AS myExpense
			    FROM group_transactions
			    WHERE group_id = #{groupId}
			      AND type = 'EXPENSE'
			      AND YEAR(date) = #{year}
			      AND MONTH(date) = #{month}
			""")
	Map<String, Object> findMonthlySummary(@Param("groupId") int groupId, @Param("userId") Long userId,
			@Param("year") int year, @Param("month") int month);

	@Update("""
			    UPDATE group_transactions
			    SET type = #{type}, amount = #{amount}, category_id = #{categoryId},
			        description = #{description}
			    WHERE id = #{id} AND group_id = #{groupId}
			""")
	void updateGroupTransaction(GroupTransaction transaction);

	@Delete("""
			    DELETE FROM group_transactions
			    WHERE id = #{transactionId} AND group_id = #{groupId}
			""")
	void deleteGroupTransaction(@Param("groupId") int groupId, @Param("transactionId") int transactionId);

	@Select("""
			    SELECT user_id, SUM(amount) as total
			    FROM group_transactions
			    WHERE group_id = #{groupId} AND type = 'EXPENSE'
			    GROUP BY user_id
			""")
	List<UserSpending> findUserExpensesByGroupId(@Param("groupId") int groupId);

}
