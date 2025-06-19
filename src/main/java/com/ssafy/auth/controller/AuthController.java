package com.ssafy.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.auth.dto.LoginRequest;
import com.ssafy.auth.dto.RegisterRequest;
import com.ssafy.auth.exception.ErrorCode;
import com.ssafy.auth.exception.exception.BussinessException;
import com.ssafy.auth.service.AuthService;
import com.ssafy.user.entity.User;
import com.ssafy.user.repository.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	
	//회원가입
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        try {
            // 이메일 중복체크
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                // BussinessException을 발생시켜서 ErrorCode.ALREADY_EXIST_EMAIL을 전달합니다.
                throw new BussinessException(ErrorCode.ALREADY_EXIST_EMAIL);
            }
            // 닉네임 중복체크 (이전 대화에서 확인된 부분이며, UserRepository에 existsByNickname이 있다고 가정)
            if (userRepository.existsByNickname(registerRequest.getNickname())){ // existsByNickname으로 수정 가정
                // BussinessException을 발생시켜서 ErrorCode.ALREADY_EXIST_NICKNAME을 전달합니다.
                throw new BussinessException(ErrorCode.ALREADY_EXIST_NICKNAME);
            }

            String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

            User newUser = User.builder()
                    .email(registerRequest.getEmail())
                    .password(hashedPassword) // 암호화된 비밀번호 설정
                    .nickname(registerRequest.getNickname()) // 닉네임 설정
                    .birthdate(registerRequest.getBirhdate()) // 
                    .gender(registerRequest.getGender())
                    .build();

            userRepository.save(newUser); // 생성된 유저 정보를 데이터베이스에 저장합니다.

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("회원가입이 성공적으로 완료되었습니다."); // 오타 수정
        } catch (BussinessException e) {
            // BussinessException이 발생하면, 해당 예외가 가진 ErrorCode의 HttpStatus와 메시지를 사용합니다.
            return ResponseEntity
                    .status(e.getErrorCode().getHttpStatus()) // ErrorCode에 정의된 HTTP 상태 코드 사용 (예: CONFLICT)
                    .body(e.getMessage()); // ErrorCode에 정의된 메시지 반환
        } catch (Exception e) {
            // 그 외 예상치 못한 모든 예외를 처리합니다.
            // 일반적으로 500 Internal Server Error를 반환하고, 실제 서비스에서는 로그를 남깁니다.
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 중 알 수 없는 오류가 발생했습니다.");
        }
	}

	
//	@PostMapping("/login")
//	public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request)
//	{
//		User user = AuthService.login(request);
//	}
}
