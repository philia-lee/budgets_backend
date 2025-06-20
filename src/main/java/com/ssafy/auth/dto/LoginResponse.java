package com.ssafy.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String accessToken;
	private String refreshToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String userEmail;
    
}
