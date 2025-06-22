package com.ssafy.auth.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ssafy.auth.dto.LoginResponse;
import com.ssafy.auth.jwt.provider.JwtProvider;
import com.ssafy.user.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	@Value("${kakao.client.id}")
    private String client_id;

    @Value("${kakao.redirect.uri}")
    private String redirect_uri;

//    private final RestTemplate restTemplate; 
    private final UserRepository userRepository; // 사용자 정보를 관리하는 레포지토리
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService; // uthService의 로그인 처리 로직
    
    @Transactional
    public LoginResponse kakaoLogin(String code)
    {
    	String tokenUrl = "https://kauth.kakao.com/oauth/token";
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); // 부여 타입*
        params.add("client_id", client_id);        //  클라이언트 ID (REST API 키)
        params.add("redirect_uri", redirect_uri);  // 리다이렉트 URI
        params.add("code", code);  //인가코드
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        try {
//        	//카카오에게 인가코드 전송
//            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
//            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            	String responseBody = response.getBody();
//            }
//        }
        return null;
    }

    
   

}
