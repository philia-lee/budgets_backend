// 예산 데이터를 나타내는 엔티티
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
    private String category;          // 카테고리 (예: 식비, 교통비)
    private Integer amount;           // 설정 예산
    private LocalDate start_date;      // 시작일
    private LocalDate end_date;        // 종료일
}
