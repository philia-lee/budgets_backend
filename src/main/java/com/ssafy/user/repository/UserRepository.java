package com.ssafy.user.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.ssafy.user.entity.User;

@Repository
public interface UserRepository{
	
	@Insert("""
			
			INSERT INTO users (email,hashed_password,nickname, birhdate, gender, created_at)
			VALUES (#{email}, #{password}, #{nickname}, #{birhdate}, #{gender}, #{createdAt)
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(User user);
	
	
	@Select("SELECT COUNT(*) FROM users WHERE email = #{email}")  //이메일 중복 체크
	Boolean existsByEmail(@Param("email") String email);
	
	@Select("SELECT * FROM users WHERE email = #{email}")
	Optional<User> findByEmail(@Param("email") String email);
	
	@Select("SELECT COUNT(*) FROM users WHERE nickname = #{nickname}")
	Boolean existsByNickname(@Param("nickname") String nickname);
	
}
