package com.ssafy.social.group.dto.response;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class GroupDetailsResponse {
    private int groupId;
    private String groupName;
    private int ownerId;
    private Date createdAt;
    private List<MemberInfo> members;

    @Data
    public static class MemberInfo {
        private int userId;
        private String nickname;
        private String role;
    }
}
