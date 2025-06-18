package com.ssafy.social.dto.request;

import lombok.Data;

@Data
public class CreateGroupRequest {
    private String groupName;
    private String description;
}
