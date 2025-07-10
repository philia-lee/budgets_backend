package com.ssafy.social.transaction.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GroupTransactionRequest {
    private String type;       // INCOME or EXPENSE
    private BigDecimal amount;
    private int categoryId;
    private String description;
}
