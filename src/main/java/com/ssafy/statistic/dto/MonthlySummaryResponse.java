// 월별 수입/ 지출/ 순이익 요약 응답
package com.ssafy.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlySummaryResponse {
    private String month;          // 예: "2024-05"
    private int income;            // 수입
    private int expense;           // 지출
    private int net;               // 수입 - 지출
}