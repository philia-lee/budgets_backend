package com.ssafy.budget.service;

import com.ssafy.budget.dto.CreateBudgetRequest;
import com.ssafy.budget.entity.Budget;
import com.ssafy.budget.repository.BudgetRepository;
import com.ssafy.notification.entity.Notification;
import com.ssafy.notification.repository.NotificationRepository;
// 예산/일림 관현 entity와 repository를 가져옴.
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service // 이 클래스가 비즈니스 로직을 처리하는 서비스 계층임을 의미
@RequiredArgsConstructor // final 필드애 대한 생성자 자동 생성
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final NotificationRepository notificationRepository;
    // 예산 정보 및 알림을 처리하기 위한 repository 주입
    
    @Transactional
    public void createBudget(Long userId,CreateBudgetRequest request)
    {
    	Budget budget = Budget.builder()
                .user_id(userId)
                .category_id(request.getCategory_id())
                .amount(request.getAmount())
                .start_date(request.getStartDate())
                .end_date(request.getEndDate())
                .build();
    	
    	budgetRepository.save(budget);
    }
    
    
    
    @Transactional // 이 메서드는 하나의 트랜잭션으로 실행되어야 함.
    public void checkAndNotifyBudgetOver(Long userId, String category, int totalSpending) {
    	// 특정 유저의 카테고리별 사용금액이 예산을 초과횄는지 검사하고, 초과 시 알림 생성
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        // 해당 유저의 모든 예산 목록을 가져옴

        for (Budget budget : budgets) {
        	// 각각의 예산에 대해 반복 실행
            if (budget.getCategory_id().equals(category)
                    && LocalDate.now().isAfter(budget.getStart_date().minusDays(1))
                    && LocalDate.now().isBefore(budget.getEnd_date().plusDays(1))
                    && totalSpending > budget.getAmount()) {
            	// 조건. 카테고리 일치하고, 예산 기간 내(StartDate~EndDate)에 있고, 사용 금액이 설정 금액보다 크다면

                Notification alarm = Notification.builder() // 초과 알림용 notification 객체 생성
                        .userId(userId)
                        .message("[" + category + "] 예산 초과 알림입니다!")
                        .type("BUDGET_OVER")
                        .isRead(false)
                        .createdAt(java.time.LocalDateTime.now())  // 시간까지 넣고 싶으면 now() 그대로 사용
                        .build();

                notificationRepository.save(alarm); // 알림을 DB에 저장
            }
     
        }
    }
}
