package com.ssafy.social.transaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
public class GroupTransactionResponse {
    private int transactionId;
    private String type;
    private BigDecimal amount;
    private int categoryId;
    private String description;
    private Date date;
}
