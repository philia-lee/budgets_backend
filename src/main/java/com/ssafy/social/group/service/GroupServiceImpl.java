package com.ssafy.social.group.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.social.group.dto.response.GroupDetailsResponse;
import com.ssafy.social.group.entity.Group;
import com.ssafy.social.group.repository.GroupRepository;
import com.ssafy.social.member.dto.response.GroupMemberResponse;
import com.ssafy.social.member.repository.GroupMemberRepository;
import com.ssafy.social.transaction.entity.GroupTransaction;
import com.ssafy.social.transaction.repository.GroupTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
	
	private final GroupRepository groupRepository;
	
	private final GroupMemberRepository groupMemberRepository;
	
	@Override
	public void createGroup(int ownerId, String name) {
		Group group = new Group();
        group.setName(name);
        group.setOwnerId(ownerId);

        groupRepository.createGroup(group);

        // 그룹 생성하면 자동으로 자신을 멤버로 추가해야 함
        groupMemberRepository.addMember(group.getId(), ownerId, "OWNER");
	}

	@Override
	public List<Group> getGroupsByUserId(int userId) {
		return groupRepository.findGroupsByUserId(userId);
	}

	@Override
	public GroupDetailsResponse getGroupDetails(int groupId, int userId) {
		if (!groupRepository.isMember(groupId, userId)) {
            throw new RuntimeException("해당 그룹에 접근 권한이 없습니다.");
        }
        
		// 그룹 기본 정보 조회
		Group group = groupRepository.findById(groupId);
		if (group == null) {
			throw new RuntimeException("그룹이 존재하지 않습니다.");
		}

		// 멤버 정보 조회
		List<GroupDetailsResponse.MemberInfo> members = groupRepository.findGroupMembers(groupId);

		// DTO 조립
		GroupDetailsResponse response = new GroupDetailsResponse();
		response.setGroupId(group.getId());
		response.setGroupName(group.getName());
		response.setOwnerId(group.getOwnerId());
		response.setCreatedAt(group.getCreatedAt());
		response.setMembers(members);

		return response;
	}

	@Override
	public void updateGroupName(int groupId, int userId, String newName) {
		Group group = groupRepository.findById(groupId);
        if (group.getOwnerId() != userId) {
            throw new RuntimeException("그룹장만 이름을 수정할 수 있습니다.");
        }
        groupRepository.updateGroupName(groupId, newName);
	}

	@Override
	public void deleteGroup(int groupId, int userId) {
		Group group = groupRepository.findById(groupId);
        if (group.getOwnerId() != userId) {
            throw new RuntimeException("그룹을 삭제할 권한이 없습니다.");
        }
        // 먼저 모든 멤버를 삭제해야함
        groupMemberRepository.removeAllMember(groupId);
        groupRepository.deleteGroup(groupId);
	}
}
