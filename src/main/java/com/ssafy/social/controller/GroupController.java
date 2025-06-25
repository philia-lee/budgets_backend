package com.ssafy.social.controller;

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
import com.ssafy.social.dto.request.AddGroupMemberRequest;
import com.ssafy.social.dto.request.GroupRequest;
import com.ssafy.social.dto.request.GroupTransactionRequest;
import com.ssafy.social.dto.response.GroupMemberInfo;
import com.ssafy.social.entity.Group;
import com.ssafy.social.entity.GroupTransaction;
import com.ssafy.social.service.GroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

// 그룹 생성, 수정, 삭제, 조회
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
	private final GroupService groupService;
	
	// 그룹 생성
    @PostMapping
    @Operation(summary = "그룹 생성")
    public ResponseEntity<?> createGroup(@UserId int userId, @RequestBody GroupRequest request) {
        groupService.createGroup(userId, request.getGroupName());
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
	
    @PutMapping("/{groupId}")
    @Operation(summary = "그룹 이름 수정")
    public ResponseEntity<?> updateGroup(@PathVariable int groupId,
                                         @UserId int userId,
                                         @RequestBody GroupRequest request) {
        groupService.updateGroupName(groupId, userId, request.getGroupName());
        return ResponseEntity.ok(Map.of("message", "그룹 이름이 수정되었습니다"));
    }
    
    @DeleteMapping("/{groupId}")
    @Operation(summary = "그룹 삭제")
    public ResponseEntity<?> deleteGroup(@PathVariable int groupId, @UserId int userId) {
        groupService.deleteGroup(groupId, userId);
        return ResponseEntity.ok(Map.of("message", "그룹이 삭제되었습니다"));
    }
	
	@PostMapping("/{groupId}/members")
	@Operation(summary = "그룹 멤버 초대")
	public ResponseEntity<?> addMember(
	        @PathVariable int groupId,
	        @UserId int userId,
	        @RequestBody AddGroupMemberRequest request) {
	    groupService.addMember(groupId, userId, request.getUserId());
	    return ResponseEntity.ok(Map.of("message", "멤버 초대 완료"));
	}

	@DeleteMapping("/{groupId}/members/{memberId}")
	@Operation(summary = "그룹 멤버 강퇴 or 자발적 탈퇴")
	public ResponseEntity<?> removeMember(
	        @PathVariable int groupId,
	        @UserId int userId,
	        @PathVariable int memberId) {
	    groupService.removeMember(groupId, userId, memberId);
	    return ResponseEntity.ok(Map.of("message", "멤버 탈퇴 처리 완료"));
	}

	@GetMapping("/{groupId}/members")
	@Operation(summary = "그룹 멤버 전체 조회")
	public ResponseEntity<?> getMembers(
	        @PathVariable int groupId,
	        @UserId int userId) {
	    List<GroupMemberInfo> members = groupService.getMembers(groupId, userId);
	    return ResponseEntity.ok(members);
	}

}
