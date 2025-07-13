package com.ssafy.statistic.service;

import com.ssafy.budget.entity.Budget;
import com.ssafy.budget.repository.BudgetRepository;
import com.ssafy.statistic.dto.*;
import com.ssafy.statistic.repository.StatisticRepository;
import com.ssafy.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;
    private final BudgetRepository budgetRepository;

    // 카테고리별 지출 합계와 비율
    public List<CategoryStatisticResponse> getCategoryExpenseStats(StatisticQueryRequest request) {
        List<Transaction> expenses = statisticRepository.findExpensesByUserAndCategoriesAndDate(
                request.getUser_id(), request.getCategory_ids(), request.getStart_date(), request.getEnd_date()
        );
        Map<Integer, Double> sumByCategory = new HashMap<>();
        double total = 0.0;
        for (Transaction t : expenses) {
            sumByCategory.merge(t.getCategory_id(), t.getAmount().doubleValue(), Double::sum);
            total += t.getAmount().doubleValue();
        }
        Map<Integer, String> categoryNameMap = getCategoryNameMap();
        List<CategoryStatisticResponse> result = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : sumByCategory.entrySet()) {
            int categoryId = entry.getKey();
            double amount = entry.getValue();
            result.add(CategoryStatisticResponse.builder()
                    .category_id(categoryId)
                    .category_name(categoryNameMap.getOrDefault(categoryId, "카테고리" + categoryId))
                    .total_amount(amount)
                    .percentage(total == 0 ? 0.0 : (amount * 100.0 / total))
                    .build());
        }
        return result;
    }

    // 월별 수입/지출/순자산 요약
    public List<MonthlySummaryResponse> getMonthlySummary(StatisticQueryRequest request) {
        List<Transaction> transactions = statisticRepository.findTransactionsByUserAndCategoriesAndDate(
                request.getUser_id(), request.getCategory_ids(), request.getStart_date(), request.getEnd_date()
        );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Map<String, List<Transaction>> byMonth = transactions.stream()
                .collect(Collectors.groupingBy(t -> sdf.format(t.getDate())));
        List<MonthlySummaryResponse> result = new ArrayList<>();
        for (String month : byMonth.keySet()) {
            double income = byMonth.get(month).stream()
                    .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                    .mapToDouble(t -> t.getAmount().doubleValue()).sum();
            double expense = byMonth.get(month).stream()
                    .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                    .mapToDouble(t -> t.getAmount().doubleValue()).sum();
            result.add(MonthlySummaryResponse.builder()
                    .month(month)
                    .income(income)
                    .expense(expense)
                    .net(income - expense)
                    .build());
        }
        result.sort(Comparator.comparing(MonthlySummaryResponse::getMonth));
        return result;
    }

    // 예산 초과 여부 판단 (특정 카테고리, 기간)
    public boolean isExpenseOverBudget(Long user_id, Integer category_id, Date start_date, Date end_date) {
        List<Transaction> expenses = statisticRepository.findExpensesByUserAndCategoriesAndDate(
                user_id, Arrays.asList(category_id), start_date, end_date
        );
        double totalExpense = expenses.stream()
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();
        List<Budget> budgets = budgetRepository.findByUserId(user_id);
        Budget matchedBudget = budgets.stream()
                .filter(b -> b.getCategory_id().equals(category_id)
                        && !start_date.before(java.sql.Date.valueOf(b.getStart_date()))
                        && !end_date.after(java.sql.Date.valueOf(b.getEnd_date())))
                .findFirst()
                .orElse(null);
        if (matchedBudget == null) return false;
        return totalExpense > matchedBudget.getAmount().doubleValue();
    }

    // 카테고리명 매핑 (실제 구현시 CategoryRepository에서 조회)
    private Map<Integer, String> getCategoryNameMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "식비");
        map.put(2, "교통비");
        map.put(3, "문화생활");
        map.put(4, "의료/건강");
        map.put(5, "쇼핑");
        map.put(6, "기타");
        return map;
    }
}
