package com.ssafy.social.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ssafy.social.entity.Group;

@Mapper
public interface GroupRepository {
	// 그룹 생성
	@Insert("""

			INSERT INTO user_groups (name,owner_id,created_at)
			VALUES (#{name}, #{ownerId}, #{createdAt)
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void create(Group group);

	// 그룹 삭제
	@Select("DELETE FROM user_groups WHERE id = #{groupId}")
	void deleteById(@Param("groupId") int groupId);

	// 그룹 이름 수정
	@Select("""
				UPDATE user_groups
				SET name = #{name}
				WHERE id = #{groupId}
			""")
	void updateName(@Param("groupId") int groupId, @Param("name") String name);

	// 내가 속한 그룹 목록 조회
	@Select("""
				SELECT ug.id, ug.name, ug.owner_id, ug.created_at
				FROM user_groups ug
				JOIN group_members gm ON ug.id = gm.group_id
				WHERE gm.user_id = #{userId}
			""")
	List<Group> findGroupsByUserId(@Param("userId") int userId);

	// 그룹ID로 특정 그룹 상세정보 가져오기
	@Select("""
				SELECT id, name, owner_id, created_at
				FROM user_groups
				WHERE id = #{groupId}
			""")
	Group findById(@Param("groupId") int groupId);

	// 해당 그룹에 내가 속해 있는지 여부 확인
	// 중복 가입 방지나 권한 확인
	@Select("""
				SELECT COUNT(*)
				FROM group_members
				WHERE group_id = #{groupId} AND user_id = #{userId}
			""")
	boolean isMember(@Param("groupId") int groupId, @Param("userId") int userId);
}
