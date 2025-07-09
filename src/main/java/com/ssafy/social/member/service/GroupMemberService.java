package com.ssafy.social.member.service;

import com.ssafy.social.member.dto.response.GroupMemberResponse;

public interface GroupMemberService {
    void inviteMember(int groupId, int inviterId, int targetUserId);
    void removeMember(int groupId, int requesterId, int targetUserId);
    GroupMemberResponse getMember(int groupId, int userId);
}
