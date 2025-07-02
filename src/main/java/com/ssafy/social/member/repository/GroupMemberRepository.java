package com.ssafy.social.member.repository;

import com.ssafy.social.member.entity.GroupMember;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupMemberRepository {

    @Insert("""
        INSERT INTO group_members (group_id, user_id, role)
        VALUES (#{groupId}, #{userId}, #{role})
    """)
    void addMember(@Param("groupId") int groupId, @Param("userId") int userId, @Param("role") String role);

    @Delete("""
        DELETE FROM group_members
        WHERE group_id = #{groupId} AND user_id = #{userId}
    """)
    void removeMember(@Param("groupId") int groupId, @Param("userId") int userId);

    @Select("""
        SELECT group_id, user_id, role
        FROM group_members
        WHERE group_id = #{groupId}
    """)
    List<GroupMember> findMembers(@Param("groupId") int groupId);
    
    @Select("SELECT nickname FROM users WHERE id = #{id}")
    String findNicknameById(@Param("id") long id);
}
