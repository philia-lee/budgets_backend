package com.ssafy.category.entity;

import java.util.Date;

import com.ssafy.transaction.entity.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
	int id;
	String name;
	String type;
	String icon;
	String color;
}
