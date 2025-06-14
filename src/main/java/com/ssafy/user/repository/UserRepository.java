package com.ssafy.user.repository;

import org.apache.ibatis.annotations.*;

import com.ssafy.user.entity.User;

@Mapper
public interface UserRepository{
	
	@Insert("""
			
			INSERT INTO users (email,password,nickname,created_at)
			VALUES (#{email}, #{password}, #{nickname}, #{createdAt)
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(User user);
	
	
	@Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
	Boolean existsByEmail(@Param("email") String email);
}
