package com.ssafy.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	private String email;
	private String password;
	private String nickname;
	
}
