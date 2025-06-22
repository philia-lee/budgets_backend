package com.ssafy.budget.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.auth.service.AuthService;
import com.ssafy.budget.dto.CreateBudgetRequest;
import com.ssafy.budget.service.BudgetService;
import com.ssafy.common.annotation.UserId;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

	private final BudgetService budgetService;
	@PostMapping
	public ResponseEntity<String> budgetcreate(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody CreateBudgetRequest request ){
		budgetService.createBudget(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
                .body("가계부 기입이 완료되었습니다");
	}
	@DeleteMapping("/{budgetId}")
	public ResponseEntity<String> deleteBudget(@Parameter(hidden =true) @UserId Long userId,  @PathVariable Long budgetId)
	{
		
		budgetService.deleteBudget(userId, budgetId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("가계부 삭제가 완료되었습니다");
	}
	
}
