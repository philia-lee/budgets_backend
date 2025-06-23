package com.ssafy.auth.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.auth.dto.LoginRequest;
import com.ssafy.auth.dto.LoginResponse;
import com.ssafy.auth.dto.RegisterRequest;
import com.ssafy.auth.service.AuthService;
import com.ssafy.auth.service.KakaoAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "회원가입과 로그인할 수 있는 기능입니다")
/*
 * 웹 요청의 **진입점(Entry Point)**이자 **프레젠테이션 계층(Presentation Layer)**입니다.
 *  클라이언트(웹 브라우저, 모바일 앱 등)의 HTTP 요청을 직접적으로 받아 처리하는 역할을 합니다.
 */
public class AuthController {
	
	 private final AuthService authService; // AuthService 주입
	 private final KakaoAuthService kakaoAuthService;
	    // 회원가입
	    @PostMapping("/register")
	    @Operation(summary = "회원가입")

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
	    @Operation(summary = "로그인")
	    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
	        // @RequestBody: HTTP 요청 본문의 JSON을 LoginRequestDto 객체로 자동 변환
	        LoginResponse response = authService.logIn(request);
	        // 성공 시 200 OK 응답과 함께 로그인 응답 DTO 반환
	        return ResponseEntity.ok(response);
	    }
	    
		@Value("${kakao.client.id}")
	    private String client_id;

	    @Value("${kakao.redirect.uri}")
	    private String redirect_uri;
	    
	    @GetMapping("/kakao/login/start") // 클라이언트가 호출할 엔드포인트
	    @Operation(summary = "카카오 로그인", description = "카카오 로그인 페이지로 보내기")
	    public ResponseEntity<Void> redirectToKakaoAuth() {
	        // application.properties 등에서 설정된 값들을 사용
	        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?" +
	                              "response_type=code" +
	                              "&client_id=" + client_id + // @Value로 주입받은 client_id
	                              "&redirect_uri=" + redirect_uri+ // @Value로 주입받은 redirect_uri
	        					   "&scope=profile_nickname,account_email";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(URI.create(kakaoAuthUrl)); // 클라이언트를 리다이렉트할 URL 설정
	        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found (리다이렉트) 응답
	    }
	    
	    
	    @GetMapping("/kako/callback")
	    @Operation(summary = "카카오 로그인 완료후")
	    public ResponseEntity<LoginResponse> kakaoLoginCallback(@RequestParam("code") String code) {
	    	try {
	            
	    		LoginResponse response = kakaoAuthService.kakaoLogin(code);
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            // 예외 발생 시 적절한 HTTP 상태 코드와 에러 메시지 반환
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body(new LoginResponse(null, null,"Bearer",0l,null))
	                                		 ;
	        }
	    	
	    }

	    

}
