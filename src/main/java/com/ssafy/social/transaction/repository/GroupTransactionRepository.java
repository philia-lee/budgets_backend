package com.ssafy.social.transaction.repository;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ssafy.social.transaction.entity.GroupTransaction;

@Mapper
public interface GroupTransactionRepository {

	@Insert("""
			    INSERT INTO group_transactions (group_id, user_id, type, amount, category_id, description, date)
			    VALUES (#{groupId}, #{userId}, #{type}, #{amount}, #{categoryId}, #{description}, #{date})
			""")
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
	List<GroupTransaction> findByGroupAndUser(@Param("groupId") int groupId, @Param("userId") int userId);
	
	// 특정 날짜 범위 조회
    @Select("""
        SELECT * FROM group_transactions
        WHERE group_id = #{groupId}
        AND date BETWEEN #{startDate} AND #{endDate}
        ORDER BY date DESC
    """)
    List<GroupTransaction> findByDateRange(@Param("groupId") int groupId,
                                           @Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate);

	@Update("""
			    UPDATE group_transactions
			    SET type = #{type}, amount = #{amount}, category_id = #{categoryId},
			        description = #{description}, date = #{date}
			    WHERE id = #{id} AND group_id = #{groupId}
			""")
	void updateGroupTransaction(GroupTransaction transaction);

	@Delete("""
			    DELETE FROM group_transactions
			    WHERE id = #{transactionId} AND group_id = #{groupId}
			""")
	void deleteGroupTransaction(@Param("groupId") int groupId, @Param("transactionId") int transactionId);

}
