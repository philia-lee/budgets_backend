package com.ssafy.social.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor //기본 생성자
public class GroupMember {
	private int id;
	private int groupId;
	private long userId;
	private String role;
}
