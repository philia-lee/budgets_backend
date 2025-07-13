package com.ssafy.transaction.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransaction {
	private Integer amount;
	private String type;
	private String category;
	private String description;
	private Date date;
}
