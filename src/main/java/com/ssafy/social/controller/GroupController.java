package com.ssafy.social.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.social.dto.request.CreateGroupTransactionRequest;
import com.ssafy.social.entity.Group;
import com.ssafy.social.entity.GroupTransaction;
import com.ssafy.social.service.GroupService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
	private final GroupService groupService;

	@GetMapping("/me")
	public ResponseEntity<?> getMyGroups() {
		try {
			// todo: 로그인된 사용자 정보 가져오기

			return null;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "조회 실패"));
		}
	}
	
	@PostMapping("/{groupId}/transactions")
	public ResponseEntity<?> createGroupTransaction(
	        @PathVariable int groupId,
	        @RequestBody CreateGroupTransactionRequest request) {
		// todo: userId 주입받아야함. jwt로직 필요

	    GroupTransaction transaction = new GroupTransaction();
	    transaction.setType(request.getType());
	    transaction.setAmount(request.getAmount());
	    transaction.setCategoryId(request.getCategoryId());
	    transaction.setDescription(request.getDescription());
	    transaction.setDate(request.getDate());

	    groupService.addGroupTransaction(groupId, transaction);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(Map.of("message", "Group transaction created successfully"));
	}

}
