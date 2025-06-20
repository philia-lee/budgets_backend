package com.ssafy.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.auth.dto.LoginRequest;
import com.ssafy.auth.dto.LoginResponse;
import com.ssafy.auth.dto.RegisterRequest;
import com.ssafy.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
/*
 * 웹 요청의 **진입점(Entry Point)**이자 **프레젠테이션 계층(Presentation Layer)**입니다.
 *  클라이언트(웹 브라우저, 모바일 앱 등)의 HTTP 요청을 직접적으로 받아 처리하는 역할을 합니다.
 */
public class AuthController {
	
	 private final AuthService authService; // AuthService 주입

	    // 회원가입
	    @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
	        // 비즈니스 로직은 서비스 계층으로 위임
	        authService.registerUser(registerRequest);
	        return ResponseEntity.status(HttpStatus.CREATED)
	                             .body("회원가입이 성공적으로 완료되었습니다.");
	        // 전역 예외 핸들러가 BussinessException(->BusinessException)을 처리하도록 맡깁니다.
	        // 여기서는 try-catch 블록을 제거합니다.
	    }

	    // 로그인
	    @PostMapping("/login")
	    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
	        // @RequestBody: HTTP 요청 본문의 JSON을 LoginRequestDto 객체로 자동 변환
	        LoginResponse response = authService.logIn(request);
	        // 성공 시 200 OK 응답과 함께 로그인 응답 DTO 반환
	        return ResponseEntity.ok(response);
	    }

}
