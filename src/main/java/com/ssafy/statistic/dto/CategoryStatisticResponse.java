package com.ssafy.statistic.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryStatisticResponse {
    private Integer category_id;
    private String category_name;
    private Double total_amount;
    private Double percentage;
}
