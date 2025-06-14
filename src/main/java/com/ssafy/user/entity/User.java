package com.ssafy.user.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
	private int id;
	private String email;
	private String password;
	private String nickname;
	private Date datetime;
	
//	public static User of(SignUpRequest request) {
//		return User.builder()
//				.email(request.email())
//				.password(request.password())
//				.nickname(request.nickanme())
//				.createdAt(LocalDateTime.now())
//				.build();
//	}
	
}
