package com.ssafy.social.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor //기본 생성자
public class Group {
	private int id;
	private String name;
	private int ownerId;
	private String createAt;
}
