package com.ssafy.statistic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StatisticQueryRequest {
    private String periodType;     // 예: "MONTHLY", "YEARLY"
    private LocalDate startDate;   // 시작일
    private LocalDate endDate;     // 종료일
}
