package com.ssafy.social.transaction.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor
public class SettlementResponse {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
}

