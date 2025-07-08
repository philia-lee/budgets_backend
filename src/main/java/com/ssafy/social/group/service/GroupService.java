package com.ssafy.social.group.service;

import java.util.List;

import com.ssafy.social.group.dto.response.GroupDetailsResponse;
import com.ssafy.social.group.entity.Group;

public interface GroupService {
	void createGroup(int ownerId, String groupName);

    List<Group> getGroupsByUserId(int userId);

    GroupDetailsResponse getGroupDetails(int groupId, int requesterId);

    void updateGroupName(int groupId, int userId, String newName);

    void deleteGroup(int groupId, int userId);
}
