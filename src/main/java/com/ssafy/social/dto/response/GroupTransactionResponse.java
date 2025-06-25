package com.ssafy.social.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class GroupTransactionResponse {
    private int transactionId;
    private String type;
    private BigDecimal amount;
    private int categoryId;
    private String description;
    private Date date;
}
