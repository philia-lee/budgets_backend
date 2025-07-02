package com.ssafy.social.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.social.group.repository.GroupRepository;
import com.ssafy.social.member.dto.response.GroupMemberResponse;
import com.ssafy.social.member.repository.GroupMemberRepository;
import com.ssafy.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {
	
	private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

	@Override
	public void inviteMember(int groupId, int inviterId, int targetUserId) {
		if (!groupRepository.isMember(groupId, inviterId)) {
            throw new RuntimeException("초대 권한이 없습니다");
        }
        if (groupRepository.isMember(groupId, targetUserId)) {
            throw new RuntimeException("이미 그룹에 속한 사용자입니다");
        }
        groupMemberRepository.addMember(groupId, targetUserId, "MEMBER");
	}

	@Override
	public void removeMember(int groupId, int requesterId, int targetUserId) {
		if (!groupRepository.isMember(groupId, requesterId)) {
            throw new RuntimeException("탈퇴 권한이 없습니다");
        }
        groupMemberRepository.removeMember(groupId, targetUserId);
	}

	@Override
	public List<GroupMemberResponse> getGroupMembers(int groupId, int requesterId) {
		if (!groupRepository.isMember(groupId, requesterId)) {
            throw new RuntimeException("조회 권한이 없습니다");
        }

        return groupMemberRepository.findMembers(groupId)
                .stream()
                .map(member -> new GroupMemberResponse(
                		member.getUserId(),
                		groupMemberRepository.findNicknameById(member.getUserId()),
                		member.getRole()))
                .collect(Collectors.toList());
	}

}
