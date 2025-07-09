package com.ssafy.social.transaction.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class GroupTransactionRequest {
	private int id;
    private String type;       // INCOME or EXPENSE
    private BigDecimal amount;
    private int categoryId;
    private String description;
    private Date date;
}
