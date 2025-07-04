package com.ssafy.social.group.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.annotation.UserId;
import com.ssafy.social.group.dto.request.CreateGroupRequest;
import com.ssafy.social.group.entity.Group;
import com.ssafy.social.group.service.GroupService;
import com.ssafy.social.member.dto.request.AddGroupMemberRequest;
import com.ssafy.social.member.dto.response.GroupMemberResponse;
import com.ssafy.social.transaction.dto.request.GroupTransactionRequest;
import com.ssafy.social.transaction.entity.GroupTransaction;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// 그룹 생성, 수정, 삭제, 조회
@RestController
@RequestMapping("/api/groups")
@Tag(name = "그룹 관리", description = "그룹 CRUD")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
public class GroupController {
	private final GroupService groupService;
	
	// 그룹 생성
    @PostMapping
    @Operation(summary = "그룹 생성")
    public ResponseEntity<?> createGroup( @RequestBody CreateGroupRequest request) {
//        groupService.createGroup(userId, request.getGroupName());
    	System.out.println(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "그룹이 생성되었습니다"));
    }

	@GetMapping("/me")
	@Operation(summary = "내가 속한 그룹 조회")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "204", description = "조회는 성공했으나 정보가 없음"),
			@ApiResponse(responseCode = "500", description = "조회 실패"), })
	public ResponseEntity<?> getMyGroups(@UserId int userId) {
		try {
			List<Group> groups = groupService.getGroupsByUserId(userId);
	        return ResponseEntity.ok(groups);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "조회 실패"));
		}
	}
	
	@GetMapping("/{groupId}")
	@Operation(summary = "그룹 상세 조회")
    public ResponseEntity<?> getGroupDetails(@PathVariable int groupId, @UserId int userId) {
        Group group = groupService.getGroupDetails(groupId, userId);
        return ResponseEntity.ok(group);
    }
	
//    @PutMapping("/{groupId}")
//    @Operation(summary = "그룹 이름 수정")
//    public ResponseEntity<?> updateGroup(@PathVariable int groupId,
//                                         @UserId int userId,
//                                         @RequestBody CreateGroupRequest request) {
//        groupService.updateGroupName(groupId, userId, request.getGroupName());
//        return ResponseEntity.ok(Map.of("message", "그룹 이름이 수정되었습니다"));
//    }
    
    @DeleteMapping("/{groupId}")
    @Operation(summary = "그룹 삭제")
    public ResponseEntity<?> deleteGroup(@PathVariable int groupId, @UserId int userId) {
        groupService.deleteGroup(groupId, userId);
        return ResponseEntity.ok(Map.of("message", "그룹이 삭제되었습니다"));
    }

}
