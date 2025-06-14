package com.ssafy.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.auth.dto.RegisterRequest;
import com.ssafy.user.repository.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	
	//회원가입
//	@PostMapping("/register")
//	public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request){
//		
//		
//		// 이메일 중복체크
//		if(userRepository.exist)
//	}
		
	

}
