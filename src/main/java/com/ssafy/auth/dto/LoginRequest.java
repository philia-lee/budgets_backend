package com.ssafy.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	
	@NotNull(message = "이메일은 필수입니다")
	@NotBlank(message = "이메일은 필수입니다")
	@Email
	String Email;
	
	@NotNull(message ="비밀번호는 필수입니다")
	@NotNull(message ="비밀번호는 필수입니다")
	String password;
	
}
