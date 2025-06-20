package com.ssafy.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.auth.dto.LoginRequest;
import com.ssafy.auth.dto.LoginResponse;
import com.ssafy.auth.dto.RegisterRequest;
import com.ssafy.auth.exception.ErrorCode;
import com.ssafy.auth.exception.exception.BusinessException;
import com.ssafy.auth.jwt.Token.AccessToken;
import com.ssafy.auth.jwt.Token.RefreshToken;
import com.ssafy.auth.jwt.provider.JwtProvider;
import com.ssafy.user.entity.User;
import com.ssafy.user.repository.UserRepository;


import lombok.*;

@Service
@RequiredArgsConstructor
/*
 * 애플리케이션의 **핵심 비즈니스 로직(Business Logic)**을 구현하는 계층입니다. 
 * 비즈니스 규칙을 적용하고, 여러 데이터 접근 계층(Repository/DAO)의 
 * 작업을 조율하여 하나의 완결된 비즈니스 기능을 수행합니다.
 */
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	
    // 회원가입 메서드
    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        // 이메일 중복 체크
    	if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_EMAIL);
        }
        // 닉네임 중복 체크
        if (userRepository.existsByNickname(registerRequest.getNickname())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_NICKNAME);
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User newUser = User.builder()
                .email(registerRequest.getEmail())
                .password(hashedPassword)
                .nickname(registerRequest.getNickname())
                .birthdate(registerRequest.getBirthdate()) 
                .gender(registerRequest.getGender())
                .build();

        userRepository.save(newUser);
        
    }
	
    @Transactional
    public LoginResponse  logIn(LoginRequest request) {
    	User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new BusinessException(ErrorCode.ILLEGAL_SIGN_IN));
        boolean passwordMatches = false;

    	passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (passwordMatches) {
		AccessToken accessToken = jwtProvider.issueAccessToken(user);
        RefreshToken refreshToken = jwtProvider.issueRefreshToken(user);
        user.setRefresh_token(refreshToken.value());
        userRepository.updateRefreshToken(user);
        return new LoginResponse(
                accessToken.value(),
                refreshToken.value(),
                "Bearer", // tokenType은 고정값이므로 직접 전달
                user.getId(),     // user 객체에서 ID 가져오기
                user.getEmail()   // user 객체에서 이메일 가져오기
            );


		}
		else
		{
			throw new BusinessException(ErrorCode.ILLEGAL_SIGN_IN);
		}
		

    }
	
}
