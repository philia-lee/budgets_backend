package com.ssafy.social.member.dto.request;

import lombok.Data;

@Data
public class AddGroupMemberRequest {
    private Long targetId;  // 초대할 사용자 id
}
