package com.ssafy.user.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.ssafy.user.entity.User;

@Mapper
public interface UserRepository{
	
	@Insert("""
            INSERT INTO users (email, hashed_password, nickname, birthdate, gender, refresh_token, created_at)
            VALUES (#{email}, #{password}, #{nickname}, #{birthdate}, #{gender}, #{refresh_token}, #{createdAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id") // DB가 생성한 ID를 User 객체의 id 필드에 주입
    void save(User user);
	
	
	@Select("SELECT COUNT(*) FROM users WHERE email = #{email}")  //이메일 중복 체크
	Boolean existsByEmail(@Param("email") String email);
	
	@Select("SELECT id, email, hashed_password as password, nickname, birthdate, gender, refresh_token, created_at as createdAt FROM users WHERE email = #{email}")
    Optional<User> findByEmail(@Param("email") String email);
	
	@Select("SELECT COUNT(*) FROM users WHERE nickname = #{nickname}")
	Boolean existsByNickname(@Param("nickname") String nickname);
	
	@Update("UPDATE users SET refresh_token = #{refresh_token} WHERE id = #{id}")
	void updateRefreshToken(User user);
	
	
}
