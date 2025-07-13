package com.ssafy.social.transaction.dto.request;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class UpdateTransactionRequest {
    private String type;         // INCOME / EXPENSE
    private BigDecimal amount;
    private int categoryId;
    private String description;
    private Date date;
}
