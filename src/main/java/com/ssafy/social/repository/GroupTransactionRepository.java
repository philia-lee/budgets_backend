package com.ssafy.social.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ssafy.social.entity.GroupTransaction;

@Mapper
public interface GroupTransactionRepository {

    @Insert("""
        INSERT INTO group_transactions (group_id, user_id, type, amount, category_id, description, date)
        VALUES (#{groupId}, #{userId}, #{type}, #{amount}, #{categoryId}, #{description}, #{date})
    """)
    void insertGroupTransaction(GroupTransaction transaction);

    @Select("""
        SELECT id, group_id, user_id, type, amount, category_id, description, date
        FROM group_transactions
        WHERE group_id = #{groupId}
    """)
    List<GroupTransaction> findByGroupId(@Param("groupId") int groupId);
}
