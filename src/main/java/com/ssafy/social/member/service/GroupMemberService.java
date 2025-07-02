package com.ssafy.social.member.service;

import java.util.List;
import com.ssafy.social.member.dto.response.GroupMemberResponse;

public interface GroupMemberService {
    void inviteMember(int groupId, int inviterId, int targetUserId);
    void removeMember(int groupId, int requesterId, int targetUserId);
    List<GroupMemberResponse> getGroupMembers(int groupId, int requesterId);
}
