package com.ssafy.budget.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBudgetRequest {
    private Long budgetId; // 수정할 예산 id
    private Integer amount;
    private LocalDate startDate;
    private LocalDate endDate;
}
