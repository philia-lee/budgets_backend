package com.ssafy.social.group.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Autowired
	private GroupTransactionRepository groupTransactionRepository;
	
	@Override
	public void createGroup(int ownerId, String groupName) {
		Group group = new Group();
        group.setName(groupName);
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
	public Group getGroupDetails(int groupId, int userId) {
		if (!groupRepository.isMember(groupId, userId)) {
            throw new RuntimeException("해당 그룹에 접근 권한이 없습니다.");
        }
        return groupRepository.findById(groupId);
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
        groupRepository.deleteGroup(groupId);
	}

//	@Override
//	public void addGroupTransaction(int groupId, GroupTransaction transaction) {
//		// 그룹 존재 및 권한 검증 (임시 생략, 이건 다음 스텝에서 구현)
//	    transaction.setGroupId(groupId);
//	    groupTransactionRepository.insertGroupTransaction(transaction);
//	}
//
//	@Override
//	public List<GroupTransaction> getGroupTransactions(int groupId) {
//		return groupTransactionRepository.findByGroupId(groupId);
//	}
//
//	@Override
//	public void updateGroupTransaction(int groupId, GroupTransaction transaction) {
//		transaction.setGroupId(groupId);
//	    groupTransactionRepository.updateGroupTransaction(transaction);
//	}
//
//	@Override
//	public void deleteGroupTransaction(int groupId, int transactionId) {
//		groupTransactionRepository.deleteGroupTransaction(groupId, transactionId);
//	}
//
//	@Override
//	public void addMember(int groupId, int userId, int newMemberId) {
//		if (!groupRepository.isMember(groupId, userId)) {
//	        throw new RuntimeException("권한 없음");
//	    }
//	    groupRepository.addGroupMember(groupId, newMemberId, "GENERAL");
//	}
//
//	@Override
//	public void removeMember(int groupId, int userId, int targetUserId) {
//		if (!groupRepository.isMember(groupId, userId)) {
//	        throw new RuntimeException("권한 없음");
//	    }
//	    groupRepository.removeGroupMember(groupId, targetUserId);
//	}
//	
//	@Override
//	public List<GroupMemberInfo> getMembers(int groupId, int userId) {
//		if (!groupRepository.isMember(groupId, userId)) {
//	        throw new RuntimeException("권한 없음");
//	    }
//	    return groupRepository.findMembersByGroupId(groupId);
//	}
}
