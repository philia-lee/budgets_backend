package com.ssafy.social.member.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddGroupMemberRequest {
    private Long targetId;  // 초대할 사용자 id
}
