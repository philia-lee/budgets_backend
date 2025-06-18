package com.ssafy.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryStatisticResponse {
    private String categoryName;   // 예: "식비", "교통비"
    private int totalAmount;       // 총 지출 금액
    private double percentage;     // 전체에서 차지하는 비율 (%)
}
