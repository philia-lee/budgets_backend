package com.ssafy.user.repository;

import org.apache.ibatis.annotations.*;

import com.ssafy.user.entity.User;

@Mapper
public interface UserRepository{
	
	@Insert("""
			
			INSERT INTO users (email,hashed_password,nickname, birhdate, gender, created_at)
			VALUES (#{email}, #{password}, #{nickname}, #{birhdate}, #{gender}, #{createdAt)
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(User user);
	
	
	@Select("SELECT COUNT(*) FROM users WHERE email = #{email}")  //이메일 중복 체크
	Boolean existsByEmail(@Param("email") String email);
}
