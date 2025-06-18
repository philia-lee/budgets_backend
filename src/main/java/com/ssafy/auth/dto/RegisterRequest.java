package com.ssafy.auth.dto;

import java.sql.Date;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	private String email;
	private String password;
	private String nickname;
	private Date birhdate;
	private String gender;
	
	
	
}
