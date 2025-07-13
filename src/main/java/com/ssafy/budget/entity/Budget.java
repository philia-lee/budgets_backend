package com.ssafy.budget.entity;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {
    private Long id;
    private Long user_id;              // 사용자 ID
    private Integer category_id;       // 카테고리 (예: 식비, 교통비)
    private Integer amount;            // 설정 예산
    private LocalDate start_date;      // 시작일
    private LocalDate end_date;        // 종료일

    @Setter(AccessLevel.PUBLIC)
    private Integer used_amount;       // ✅ 이번 달 지출 금액 (DB에는 저장되지 않음)
}
