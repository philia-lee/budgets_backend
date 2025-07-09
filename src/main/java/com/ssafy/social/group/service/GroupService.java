package com.ssafy.social.group.service;

import java.util.List;

import com.ssafy.social.group.dto.response.GroupDetailsResponse;
import com.ssafy.social.group.entity.Group;

public interface GroupService {
	void createGroup(Long ownerId, String groupName);

    List<Group> getGroupsByUserId(Long userId);

    GroupDetailsResponse getGroupDetails(int groupId, Long requesterId);

    void updateGroupName(int groupId, Long userId, String newName);

    void deleteGroup(int groupId, Long userId);
}
