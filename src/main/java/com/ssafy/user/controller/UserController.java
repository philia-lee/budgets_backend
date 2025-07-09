package com.ssafy.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.annotation.UserId;
import com.ssafy.user.dto.profileResponse;
import com.ssafy.user.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	
	public ResponseEntity<List<profileResponse>> profile(@Parameter(hidden =true) @UserId Long userId)
	{
		List<profileResponse> profile =  service.profile(userId);
		return ResponseEntity.ok(profile);
	}
	
}
