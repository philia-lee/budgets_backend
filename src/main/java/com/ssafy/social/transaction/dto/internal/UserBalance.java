package com.ssafy.social.transaction.dto.internal;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserBalance {
    private Long userId;
    private BigDecimal amount;
}

