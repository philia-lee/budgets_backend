package com.ssafy.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.social.entity.Group;
import com.ssafy.social.entity.GroupTransaction;
import com.ssafy.social.repository.GroupRepository;
import com.ssafy.social.repository.GroupTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
	
	private final GroupRepository groupRepository;
	
	@Autowired
	private GroupTransactionRepository groupTransactionRepository;

	@Override
	public List<Group> getMyGroups(int userId) {
		return groupRepository.findGroupsByUserId(userId);
	}

	@Override
	public void addGroupTransaction(int groupId, GroupTransaction transaction) {
		// 그룹 존재 및 권한 검증 (임시 생략, 이건 다음 스텝에서 구현)
	    transaction.setGroupId(groupId);
	    groupTransactionRepository.insertGroupTransaction(transaction);
	}
}
