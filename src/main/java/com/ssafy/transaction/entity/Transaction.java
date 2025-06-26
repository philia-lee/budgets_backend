package com.ssafy.transaction.entity;

import java.time.LocalDate;
import java.util.Date;

import com.ssafy.budget.entity.Budget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
	private String type;
	private Integer amount;
	private Integer category_id;
	private String description;
	private Date date;
	private Date created_at;
	
	
	
}
