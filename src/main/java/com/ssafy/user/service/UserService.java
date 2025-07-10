package com.ssafy.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.user.dto.profileRequest;
import com.ssafy.user.dto.profileResponse;
import com.ssafy.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository respository;
	@Transactional(readOnly = true) 
	public profileResponse profile(Long userId)
	{
		return respository.profile(userId);
	}
	
	@Transactional 
	public void updateProfile(Long userId, profileRequest profileRequest) {
		
		respository.updateUserProfile(userId, profileRequest);
	}
	
}
