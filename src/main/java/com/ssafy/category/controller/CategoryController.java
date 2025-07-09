package com.ssafy.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.category.dto.CreateCategory;
import com.ssafy.category.dto.CategoryResponse;
import com.ssafy.category.service.CategoryService;
import com.ssafy.common.annotation.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@Tag(name="카테고리")
@SecurityRequirement(name = "JWT")
public class CategoryController {

	private final CategoryService service;
	
	@PostMapping
	@Operation(summary="등록하기")
	ResponseEntity<String> createcategory(@Parameter(hidden = true) @UserId Long userId,@Valid @RequestBody CreateCategory request)
	{
		service.create(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
                .body("카테고리 생성이 완료되었습니다");
	}
	
	@GetMapping
	@Operation(summary="조회하기")
	public ResponseEntity<List<CategoryResponse>> readAllCategories(@Parameter(hidden = true) @UserId Long userId) {

		List<CategoryResponse> categories = service.allshow(userId); 
		
		// 200 OK 상태 코드와 함께 조회된 카테고리 목록 반환
		return ResponseEntity.ok(categories);
	}
}
