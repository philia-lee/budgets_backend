package com.ssafy.transaction.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionResponse {
	int id;
	String type;
	int amount;
	String category;
	String description;
	Date date;
}
