package com.ssafy.social.transaction.dto.internal;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UserSpending {
    private int userId;
    private BigDecimal total;
}
