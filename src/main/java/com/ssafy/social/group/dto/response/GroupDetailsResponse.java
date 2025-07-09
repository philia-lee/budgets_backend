package com.ssafy.social.group.dto.response;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class GroupDetailsResponse {
    private int groupId;
    private String groupName;
    private Long ownerId;
    private Date createdAt;
    private List<MemberInfo> members;

    @Data
    public static class MemberInfo {
        private Long userId;
        private String nickname;
        private String role;
    }
}
