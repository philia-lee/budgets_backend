package com.ssafy.social.member.repository;

import com.ssafy.social.member.dto.response.GroupMemberResponse;
import com.ssafy.user.entity.User;

import org.apache.ibatis.annotations.*;

@Mapper
public interface GroupMemberRepository {

	@Insert("""
			    INSERT INTO group_members (group_id, user_id, role)
			    VALUES (#{groupId}, #{targetId}, #{role})
			""")
	void addMember(@Param("groupId") int groupId, @Param("targetId") Long targetId, @Param("role") String role);

	@Delete("""
			    DELETE FROM group_members
			    WHERE group_id = #{groupId} AND user_id = #{userId}
			""")
	void removeMember(@Param("groupId") int groupId, @Param("userId") Long userId);

	@Delete("""
			    DELETE FROM group_members
			    WHERE group_id = #{groupId}
			""")
	void removeAllMember(@Param("groupId") int groupId);

	@Select("""
			    SELECT
			        gm.user_id AS userId,
			        u.nickname AS nickname,
			        gm.role AS role
			    FROM group_members gm
			    JOIN users u ON gm.user_id = u.id
			    WHERE gm.group_id = #{groupId} AND gm.user_id = #{userId}
			""")
	GroupMemberResponse findMember(@Param("groupId") int groupId, @Param("userId") Long userId);

	@Select("""
			    SELECT id, nickname
			    FROM users
			    WHERE nickname = #{nickname}
			""")
	User findByNickname(@Param("nickname") String nickname);
}
