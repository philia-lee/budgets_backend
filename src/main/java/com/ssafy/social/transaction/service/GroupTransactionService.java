package com.ssafy.social.transaction.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.social.transaction.entity.GroupTransaction;
import com.ssafy.social.transaction.repository.GroupTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupTransactionService {

	private final GroupTransactionRepository transactionRepository;

	// 거래 등록
	public void createTransaction(int groupId, int userId, GroupTransaction transaction) {
		transaction.setGroupId(groupId);
		transaction.setUserId(userId);
		transactionRepository.insertGroupTransaction(transaction);
	}

	// 거래 수정
	public void updateTransaction(GroupTransaction transaction) {
		transactionRepository.updateGroupTransaction(transaction);
	}

	// 그룹 전체 거래 조회
	public List<GroupTransaction> getTransactionsByGroup(int groupId) {
		return transactionRepository.findByGroupId(groupId);
	}

	// 특정 사용자 거래 조회
	public List<GroupTransaction> getTransactionsByGroupAndUser(int groupId, int userId) {
		return transactionRepository.findByGroupAndUser(groupId, userId);
	}

	// 날짜 범위 조회
	public List<GroupTransaction> getTransactionsByDateRange(int groupId, Date startDate, Date endDate) {
		return transactionRepository.findByDateRange(groupId, startDate, endDate);
	}

	// 거래 삭제
	public void deleteTransaction(int groupId, int transactionId) {
		transactionRepository.deleteGroupTransaction(groupId, transactionId);
	}
}
