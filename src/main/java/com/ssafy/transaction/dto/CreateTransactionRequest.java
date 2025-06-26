package com.ssafy.transaction.dto;

import java.time.LocalDate;
import java.util.Date;

import com.ssafy.budget.dto.CreateBudgetRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 생성
public class CreateTransactionRequest {
	
	private Integer amount;
	private String type;
	private Integer category_id;
	private String description;
	private Date date;
	
}
