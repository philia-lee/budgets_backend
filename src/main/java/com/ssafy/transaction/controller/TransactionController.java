package com.ssafy.transaction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ssafy.common.annotation.UserId;
import com.ssafy.transaction.dto.CreateTransactionRequest;
import com.ssafy.transaction.dto.TransactionResponse;
import com.ssafy.transaction.dto.UpdateTransaction;
import com.ssafy.transaction.service.TransactionService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trans")
@RequiredArgsConstructor
@Tag(name="금액 기입")
@SecurityRequirement(name = "JWT")
public class TransactionController {

	private final TransactionService transactionService;
	
	@PostMapping
	@Operation(summary="기입하기")
	public ResponseEntity<String> create_transaction(@Parameter(hidden = true) @UserId Long userId,@Valid @RequestBody CreateTransactionRequest request)
	{
		transactionService.create(userId,request);
		return ResponseEntity.status(HttpStatus.CREATED)
                .body("기입이 완료되었습니다");
	}
	
	@PatchMapping("/{transactionId}")
	@Operation(summary="수정하기")
	public ResponseEntity<String> update_transaction(@Parameter(hidden = true) @UserId Long userId,@PathVariable Long transactionId ,@Valid @RequestBody UpdateTransaction request) 
	{
		transactionService.Update(userId, transactionId, request);
		return ResponseEntity.status(HttpStatus.OK)
				.body("수정이 완료되었습니다");
	}
	
	@DeleteMapping("/{transactionId}")
	@Operation(summary="삭제하기")
	public ResponseEntity<String> delete_transaction(@Parameter(hidden = true) @UserId Long userId,@PathVariable Long transactionId)
	{
		transactionService.Delete(userId, transactionId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("삭제가 완료되었습니다"); 
	}
	
	@GetMapping
	public ResponseEntity<List<TransactionResponse>> readAllTransaction(@Parameter(hidden = true) @UserId Long userId)
	{
		List<TransactionResponse> transaction = transactionService.allshow(userId);
		return ResponseEntity.ok(transaction);
				
	}
	
}
