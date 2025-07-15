package com.ssafy.social.member.service;

import org.springframework.stereotype.Service;

import com.ssafy.social.group.repository.GroupRepository;
import com.ssafy.social.member.dto.response.GroupMemberResponse;
import com.ssafy.social.member.repository.GroupMemberRepository;
import com.ssafy.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {
	
	private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

	@Override
	public void inviteMember(int groupId, Long inviterId, Long targetId) {
        if (groupRepository.isMember(groupId, targetId)) {
            throw new RuntimeException("이미 그룹에 속한 사용자입니다");
        }
        groupMemberRepository.addMember(groupId, targetId, "MEMBER");
	}

	@Override
	public void removeMember(int groupId, Long requesterId, Long targetUserId) {
		if (!groupRepository.isMember(groupId, requesterId)) {
            throw new RuntimeException("탈퇴 권한이 없습니다");
        }
        groupMemberRepository.removeMember(groupId, targetUserId);
	}

	@Override
	public GroupMemberResponse getMember(int groupId, Long userId) {
		GroupMemberResponse member = groupMemberRepository.findMember(groupId, userId);
        if (member == null) {
            throw new RuntimeException("해당 그룹에 해당 유저가 존재하지 않습니다.");
        }
        return member;
	}

	@Override
	public User getUserByNickname(String nickname) {
		User user = groupMemberRepository.findByNickname(nickname);
        if (user == null) {
            throw new RuntimeException("닉네임 [" + nickname + "]인 사용자가 존재하지 않습니다.");
        }
        return user;
	}
}
