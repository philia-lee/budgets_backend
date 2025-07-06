package com.ssafy.social.transaction.controller;

import com.ssafy.social.group.service.GroupService;
import com.ssafy.social.transaction.dto.request.GroupTransactionRequest;
import com.ssafy.social.transaction.dto.response.GroupTransactionResponse;
import com.ssafy.social.transaction.dto.response.SettlementResponse;
import com.ssafy.social.transaction.entity.GroupTransaction;
import com.ssafy.social.transaction.service.GroupTransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.common.annotation.UserId;  // 기존에 잘 쓰고 있는 userId 주입 어노테이션

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups/{groupId}/transactions")
@Tag(name = "그룹 거래 관리", description = "거래 CRUD, 정산")
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
public class GroupTransactionController {

    private final GroupTransactionService service;

    @PostMapping
    @Operation(summary = "그룹 거래 생성")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "생성 성공"),
		@ApiResponse(responseCode = "500", description = "생성 실패"), })
    public ResponseEntity<?> createGroupTransaction(
            @PathVariable int groupId,
            @UserId int userId,
            @RequestBody GroupTransactionRequest request) {

    	GroupTransaction transaction = toEntity(request);
        transaction.setGroupId(groupId);
        transaction.setUserId(userId);

        service.createTransaction(groupId, userId, transaction);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Group transaction created successfully"));
    }
    
    @PutMapping("/{transactionId}")
    @Operation(summary = "거래 수정")
    public ResponseEntity<?> updateGroupTransaction(
            @PathVariable int groupId,
            @PathVariable int transactionId,
            @UserId int userId,
            @RequestBody GroupTransactionRequest request) {

    	GroupTransaction transaction = toEntity(request);
        transaction.setId(transactionId);
        transaction.setGroupId(groupId);
        transaction.setUserId(userId);

        service.updateTransaction(transaction);

        return ResponseEntity.ok(Map.of("message", "Group transaction updated successfully"));
    }
    
    @GetMapping
    @Operation(summary = "모든 거래 조회")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "204", description = "조회는 성공했으나 정보가 없음"),
		@ApiResponse(responseCode = "500", description = "조회 실패"), })
    public ResponseEntity<List<GroupTransactionResponse>> getAll(@PathVariable int groupId) {
        List<GroupTransaction> list = service.getTransactionsByGroup(groupId);
        return ResponseEntity.ok(toResponseList(list));
    }
    
    @GetMapping("/user")
    @Operation(summary = "내가 등록한 거래 조회")
    public ResponseEntity<List<GroupTransactionResponse>> getByUser(@PathVariable int groupId,
                                                                    @UserId int userId) {
        List<GroupTransaction> list = service.getTransactionsByGroupAndUser(groupId, userId);
        return ResponseEntity.ok(toResponseList(list));
    }
    
    @GetMapping("/period")
    @Operation(summary = "특정 기간 동안 모든 거래 조회")
    public ResponseEntity<List<GroupTransactionResponse>> getByPeriod(@PathVariable int groupId,
                                                                      @RequestParam Date startDate,
                                                                      @RequestParam Date endDate) {
        List<GroupTransaction> list = service.getTransactionsByDateRange(groupId, startDate, endDate);
        return ResponseEntity.ok(toResponseList(list));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteGroupTransaction(
            @PathVariable int groupId,
            @PathVariable int transactionId) {

        service.deleteTransaction(groupId, transactionId);
        return ResponseEntity.ok(Map.of("message", "Group transaction deleted successfully"));
    }
    
    // 정산 결과 조회
    @GetMapping("/settlement")
    @Operation(summary = "그룹 정산 결과 계산", description = "그룹의 지출을 바탕으로 정산 내역을 계산합니다.")
    public ResponseEntity<List<SettlementResponse>> getSettlement(@PathVariable int groupId) {
        List<SettlementResponse> result = service.calculateSettlement(groupId);
        return ResponseEntity.ok(result);
    }

    // 변환 메서드
    private GroupTransaction toEntity(GroupTransactionRequest request) {
        GroupTransaction entity = new GroupTransaction();
        entity.setId(request.getId());
        entity.setType(request.getType());
        entity.setAmount(request.getAmount());
        entity.setCategoryId(request.getCategoryId());
        entity.setDescription(request.getDescription());
        entity.setDate(request.getDate());
        return entity;
    }
    
    private List<GroupTransactionResponse> toResponseList(List<GroupTransaction> list) {
        return list.stream()
                .map(tx -> new GroupTransactionResponse(
                        tx.getId(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getCategoryId(),
                        tx.getDescription(),
                        tx.getDate()))
                .toList();
    }
}
