package com.ssafy.social.member.service;

import com.ssafy.social.member.dto.response.GroupMemberResponse;

public interface GroupMemberService {
    void inviteMember(int groupId, Long inviterId, Long targetUserId);
    void removeMember(int groupId, Long requesterId, Long targetUserId);
    GroupMemberResponse getMember(int groupId, Long userId);
}
