package com.ssafy.budget.service;

import com.ssafy.auth.exception.ErrorCode;
import com.ssafy.auth.exception.exception.BusinessException;
import com.ssafy.budget.dto.CreateBudgetRequest;
import com.ssafy.budget.dto.UpdateBudgetRequest;
import com.ssafy.budget.entity.Budget;
import com.ssafy.budget.repository.BudgetRepository;
import com.ssafy.notification.entity.Notification;
import com.ssafy.notification.repository.NotificationRepository;
import com.ssafy.transaction.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final NotificationRepository notificationRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void createBudget(Long userId, CreateBudgetRequest request) {
    	System.out.println("asd"+request.getStartDate());
        Budget budget = Budget.builder()
                .user_id(userId)
                .category_id(request.getCategory_id())
                .amount(request.getAmount())
                .start_date(request.getStartDate()) // [수정] camelCase → snake_case
                .end_date(request.getEndDate())     // [수정] camelCase → snake_case
                .build();
        budgetRepository.save(budget);
    }

    @Transactional
    public void deleteBudget(Long userId, Long budgetId) {
        Boolean existingBudget = budgetRepository.existsByIdAndUserId(userId, budgetId);
        if (!existingBudget) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
        budgetRepository.delete(userId, budgetId);
    }

    @Transactional
    public void updateBudget(Long userId, Long budgetId, UpdateBudgetRequest request) {
        Boolean existingBudget = budgetRepository.existsByIdAndUserId(userId, budgetId);
        if (!existingBudget) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
        Budget budget = Budget.builder()
                .id(budgetId)
                .user_id(userId)
                .category_id(request.getCategory_id())
                .amount(request.getAmount())
                .start_date(request.getStartDate())
                .end_date(request.getEndDate())
                .build();
        budgetRepository.update(budget, budgetId, userId);
    }

    @Transactional
    public void checkAndNotifyBudgetOver(Long userId, String category, int totalSpending) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        for (Budget budget : budgets) {
            if (budget.getCategory_id().equals(category)
                    && LocalDate.now().isAfter(budget.getStart_date().minusDays(1))
                    && LocalDate.now().isBefore(budget.getEnd_date().plusDays(1))
                    && totalSpending > budget.getAmount()) {
                Notification alarm = Notification.builder()
                        .userId(userId)
                        .message("[" + category + "] 예산 초과 알림입니다!")
                        .type("BUDGET_OVER")
                        .isRead(false)
                        .createdAt(java.time.LocalDateTime.now())
                        .build();
                notificationRepository.save(alarm);
            }
        }
    }

    // ✅ used_amount 채워서 예산 반환
    @Transactional(readOnly = true)
    public List<Budget> getBudgetsWithUsage(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        for (Budget budget : budgets) {
            Integer totalSpent = transactionRepository.getTotalSpentForCategory(
                    userId,
                    budget.getCategory_id(),
                    budget.getStart_date(),
                    budget.getEnd_date()
            );
            budget.setUsed_amount(totalSpent);
        }
        return budgets;
    }
}
