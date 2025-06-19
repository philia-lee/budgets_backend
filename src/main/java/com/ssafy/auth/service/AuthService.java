package com.ssafy.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.auth.dto.LoginRequest;
import com.ssafy.auth.exception.ErrorCode;
import com.ssafy.auth.exception.exception.BussinessException;
import com.ssafy.user.entity.User;
import com.ssafy.user.repository.UserRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/*
 * 애플리케이션의 **핵심 비즈니스 로직(Business Logic)**을 구현하는 계층입니다. 
 * 비즈니스 규칙을 적용하고, 여러 데이터 접근 계층(Repository/DAO)의 
 * 작업을 조율하여 하나의 완결된 비즈니스 기능을 수행합니다.
 */
public class AuthService {

//	private final UserRepository userRepository;
//	
//	@Transactional
//	public User logIn(LoginRequest request) {
//		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new BusinessException(ErrorCode.ILLEGAL_SIGN_IN));
//
//		if (!user.checkPassword(request.getPassword())) {
//			throw new BussinessException(ErrorCode.ILLEGAL_SIGN_IN);
//		}
//
//		return user;
	

	
}
