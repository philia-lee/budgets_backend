package com.ssafy.budget.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBudgetRequest {
    private Integer category_id;
    private Integer amount;
    private LocalDate startDate; // [수정] camelCase
    private LocalDate endDate;   // [수정] camelCase
}
