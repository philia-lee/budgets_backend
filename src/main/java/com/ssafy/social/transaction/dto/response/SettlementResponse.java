package com.ssafy.social.transaction.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor
public class SettlementResponse {
    private int fromUserId;
    private int toUserId;
    private BigDecimal amount;
}

