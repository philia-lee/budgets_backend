package com.ssafy.social.controller;

import com.ssafy.social.dto.request.GroupTransactionRequest;
import com.ssafy.social.dto.response.GroupTransactionResponse;
import com.ssafy.social.entity.GroupTransaction;
import com.ssafy.social.service.GroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.common.annotation.UserId;  // 기존에 잘 쓰고 있는 userId 주입 어노테이션

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups/{groupId}/transactions")
@RequiredArgsConstructor
public class GroupTransactionController {

    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "그룹 거래 생성")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "생성 성공"),
		@ApiResponse(responseCode = "500", description = "생성 실패"), })
    public ResponseEntity<?> createGroupTransaction(
            @PathVariable int groupId,
            @UserId int userId,
            @RequestBody GroupTransactionRequest request) {

        GroupTransaction transaction = new GroupTransaction();
        transaction.setGroupId(groupId);
        transaction.setUserId(userId);
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setCategoryId(request.getCategoryId());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());

        groupService.addGroupTransaction(groupId, transaction);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Group transaction created successfully"));
    }

    @GetMapping
    @Operation(summary = "그룹 거래 조회")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "204", description = "조회는 성공했으나 정보가 없음"),
		@ApiResponse(responseCode = "500", description = "조회 실패"), })
    public ResponseEntity<?> getGroupTransactions(
            @PathVariable int groupId,
            @UserId int userId) {

        List<GroupTransaction> transactions = groupService.getGroupTransactions(groupId);

        // 엔티티 → 응답 DTO 변환
        List<GroupTransactionResponse> response = transactions.stream().map(t -> {
            GroupTransactionResponse dto = new GroupTransactionResponse();
            dto.setTransactionId(t.getId());
            dto.setType(t.getType());
            dto.setAmount(t.getAmount());
            dto.setCategoryId(t.getCategoryId());
            dto.setDescription(t.getDescription());
            dto.setDate(t.getDate());
            return dto;
        }).toList();

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{transactionId}")
    @Operation(summary = "거래 수정")
    public ResponseEntity<?> updateGroupTransaction(
            @PathVariable int groupId,
            @PathVariable int transactionId,
            @UserId int userId,
            @RequestBody GroupTransactionRequest request) {

        GroupTransaction transaction = new GroupTransaction();
        transaction.setId(transactionId);
        transaction.setGroupId(groupId);
        transaction.setUserId(userId);
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setCategoryId(request.getCategoryId());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());

        groupService.updateGroupTransaction(groupId, transaction);

        return ResponseEntity.ok(Map.of("message", "Group transaction updated successfully"));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteGroupTransaction(
            @PathVariable int groupId,
            @PathVariable int transactionId,
            @UserId int userId) {

        groupService.deleteGroupTransaction(groupId, transactionId);
        return ResponseEntity.ok(Map.of("message", "Group transaction deleted successfully"));
    }

}
