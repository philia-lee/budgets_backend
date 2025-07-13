package com.ssafy.statistic.controller;

import com.ssafy.statistic.dto.*;
import com.ssafy.statistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @PostMapping("/category-expense")
    public List<CategoryStatisticResponse> getCategoryExpenseStats(@RequestBody StatisticQueryRequest request) {
        return statisticService.getCategoryExpenseStats(request);
    }

    @PostMapping("/monthly-summary")
    public List<MonthlySummaryResponse> getMonthlySummary(@RequestBody StatisticQueryRequest request) {
        return statisticService.getMonthlySummary(request);
    }

    @GetMapping("/over-budget")
    public boolean isExpenseOverBudget(
            @RequestParam Long user_id,
            @RequestParam Integer category_id,
            @RequestParam java.util.Date start_date,
            @RequestParam java.util.Date end_date) {
        return statisticService.isExpenseOverBudget(user_id, category_id, start_date, end_date);
    }
}
