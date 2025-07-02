package com.ssafy.social.transaction.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.social.transaction.dto.internal.UserBalance;
import com.ssafy.social.transaction.dto.internal.UserSpending;
import com.ssafy.social.transaction.dto.response.SettlementResponse;
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
	
	public List<SettlementResponse> calculateSettlement(int groupId) {
	    // 1. 해당 그룹의 모든 사용자별 지출 총합 조회
	    List<UserSpending> spendings = transactionRepository.findUserExpensesByGroupId(groupId);

	    if (spendings.isEmpty()) {
	        return List.of(); // 지출이 없다면 정산할 필요 없음
	    }

	    // 2. 총 지출 계산
	    BigDecimal total = spendings.stream()
	            .map(UserSpending::getTotal)
	            .reduce(BigDecimal.ZERO, BigDecimal::add);

	    // 3. 평균 지출 = 총 지출 / 인원 수
	    int memberCount = spendings.size();
	    BigDecimal average = total.divide(BigDecimal.valueOf(memberCount), 2, RoundingMode.HALF_UP);

	    // 4. 사용자별 지출과 평균 지출 차이를 계산해서
	    //    초과 지출한 사람(받을 사람)과 부족 지출한 사람(줄 사람)으로 분리
	    List<UserBalance> plus = new ArrayList<>();
	    List<UserBalance> minus = new ArrayList<>();

	    for (UserSpending spending : spendings) {
	        BigDecimal diff = spending.getTotal().subtract(average);
	        if (diff.compareTo(BigDecimal.ZERO) > 0) {
	            plus.add(new UserBalance(spending.getUserId(), diff)); // 받을 돈
	        } else if (diff.compareTo(BigDecimal.ZERO) < 0) {
	            minus.add(new UserBalance(spending.getUserId(), diff.abs())); // 줄 돈
	        }
	    }

	    // 5. 실제 정산 결과 생성
	    List<SettlementResponse> result = new ArrayList<>();
	    int i = 0, j = 0;

	    // minus: 줄 사람 리스트
	    // plus: 받을 사람 리스트
	    while (i < minus.size() && j < plus.size()) {
	        UserBalance m = minus.get(i);
	        UserBalance p = plus.get(j);

	        // 둘 중 적은 금액만큼 정산
	        BigDecimal settleAmount = m.getAmount().min(p.getAmount());
	        result.add(new SettlementResponse(m.getUserId(), p.getUserId(), settleAmount));

	        // 남은 금액 갱신
	        m.setAmount(m.getAmount().subtract(settleAmount));
	        p.setAmount(p.getAmount().subtract(settleAmount));

	        // 0원이 되면 다음 사람으로
	        if (m.getAmount().compareTo(BigDecimal.ZERO) == 0) i++;
	        if (p.getAmount().compareTo(BigDecimal.ZERO) == 0) j++;
	    }

	    return result;
	}

}
