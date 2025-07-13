package com.ssafy.user.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.ssafy.user.dto.profileRequest;
import com.ssafy.user.dto.profileResponse;
import com.ssafy.user.entity.User;

@Mapper
public interface UserRepository{
	
	@Insert("""
            INSERT INTO users (email, hashed_password, nickname, birthdate, gender, refresh_token)
            VALUES (#{email}, #{password}, #{nickname}, #{birthdate}, #{gender}, #{refresh_token})
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
	
	@Select("SELECT email, nickname, birthdate, gender,created_at FROM users WHERE id= #{id}")
	profileResponse profile(@Param("id")Long userId);
	
	
	@Update("""
	        <script>
	        UPDATE users
	        <set>
	            <if test='profileRequest.nickname != null and !profileRequest.nickname.trim().isEmpty()'>
	                nickname = #{profileRequest.nickname},
	            </if>
	            <if test='profileRequest.birthdate != null'>
	                birthdate = #{profileRequest.birthdate},
	            </if>
	            <if test='profileRequest.gender != null and !profileRequest.gender.trim().isEmpty()'>
	                gender = #{profileRequest.gender},
	            </if>
	            id = id
	        </set>
	        WHERE id = #{userId}
	        </script>
	        """)
	void updateUserProfile(@Param("userId") Long userId, @Param("profileRequest") profileRequest profileRequest);
}
