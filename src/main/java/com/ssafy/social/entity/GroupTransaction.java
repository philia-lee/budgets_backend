package com.ssafy.social.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor //기본 생성자
public class GroupTransaction {
	int id;
	int groupId;
	int userId;
	String role;
}
