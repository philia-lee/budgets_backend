package com.ssafy.social.dto.response;

import lombok.Data;

@Data
public class GroupMemberInfo {
    private int userId;
    private String nickname;
    private String role;
}

