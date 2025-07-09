package com.ssafy.social.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMemberResponse {
	private long userId;
    private String nickname;
    private String role;
}

