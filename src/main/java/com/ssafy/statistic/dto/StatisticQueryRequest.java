package com.ssafy.statistic.dto;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StatisticQueryRequest {
    private Long user_id;
    private Date start_date;
    private Date end_date;
    private List<Integer> category_ids; // null이면 전체
}
