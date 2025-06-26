package com.ssafy.social.dto.request;

import lombok.Data;

@Data
public class AddGroupMemberRequest {
    private int userId;  // 초대할 사용자 id
}
