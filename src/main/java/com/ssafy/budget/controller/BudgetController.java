package com.ssafy.budget.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.auth.service.AuthService;
import com.ssafy.budget.dto.CreateBudgetRequest;
import com.ssafy.budget.dto.UpdateBudgetRequest;
import com.ssafy.budget.service.BudgetService;
import com.ssafy.common.annotation.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
@Tag(name = "가계부 관리", description = "사용자 가계부 항목을 생성, 조회, 수정, 삭제하는 API")
@SecurityRequirement(name = "JWT")
public class BudgetController {

	private final BudgetService budgetService;
	@PostMapping
	@Operation(summary = "새로운 가계부 항목 생성", description = "현재 로그인된 사용자를 위한 새로운 가계부 항목을 생성합니다.")
	
	public ResponseEntity<String> createbudget(@Parameter(hidden = true) @UserId Long userId,@Valid @RequestBody CreateBudgetRequest request ){
		budgetService.createBudget(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
                .body("가계부 기입이 완료되었습니다");
	}
	
	@Operation(summary = "새로운 가계부 항목 삭제", description = "현재 로그인된 사용자를 위한 가계부 항목을 삭제합니다.")
	@DeleteMapping("/{budgetId}")
	public ResponseEntity<String> deleteBudget(@Parameter(hidden =true) @UserId Long userId,@Parameter(name = "budgetId", description = "가계부 id", in = ParameterIn.PATH)  @PathVariable Long budgetId)
	{
		
		budgetService.deleteBudget(userId, budgetId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("가계부 삭제가 완료되었습니다");
	}
	
	@PatchMapping("/{budgetId}")
	@Operation(summary = "새로운 가계부 항목 수정", description = "현재 로그인된 사용자를 위한 가계부 항목을 수정합니다.")
	public ResponseEntity<String> updateBudget(@Parameter(hidden =true) @UserId Long userId,  @PathVariable Long budgetId, @Valid @RequestBody UpdateBudgetRequest request)
	{
		budgetService.updateBudget(userId,budgetId,request);
	return ResponseEntity.status(HttpStatus.OK)
			.body("가계부 수정이 완료되었습니다");
	}
	
}
