package com.ssafy.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.annotation.UserId;
import com.ssafy.user.dto.profileRequest;
import com.ssafy.user.dto.profileResponse;
import com.ssafy.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Tag(name = "프로필", description = "사용자 프로필을 수정 조회하는 API")
@SecurityRequirement(name = "JWT")
public class UserController {

	private final UserService service;
	
	@GetMapping
	@Operation(summary = "프로필 조회")
	public ResponseEntity<profileResponse> profile(@Parameter(hidden =true) @UserId Long userId)
	{
		profileResponse profile =  service.profile(userId);
		return ResponseEntity.ok(profile);
	}
	
	
	@PatchMapping
	@Operation(summary = "프로필 수정")
	public ResponseEntity<String> updateprofile(@Parameter(hidden = true) @UserId Long userId,@RequestBody profileRequest profileRequest)

	{
		service.updateProfile(userId, profileRequest);
		return ResponseEntity.status(HttpStatus.OK)
				.body("프로필 수정이 완료되었습니다");
	}
	
}
