package com.ssafy.social.service;

import java.util.List;

import com.ssafy.social.entity.Group;
import com.ssafy.social.entity.GroupTransaction;

public interface GroupService {
	List<Group> getMyGroups(int userId);
	
	void addGroupTransaction(int groupId, GroupTransaction transaction);
}
