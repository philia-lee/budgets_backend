package com.ssafy.social.group.service;

import java.util.List;

import com.ssafy.social.group.entity.Group;
import com.ssafy.social.member.dto.response.GroupMemberResponse;
import com.ssafy.social.transaction.entity.GroupTransaction;

public interface GroupService {
	void createGroup(int ownerId, String groupName);

    List<Group> getGroupsByUserId(int userId);

    Group getGroupDetails(int groupId, int userId);

    void updateGroupName(int groupId, int userId, String newName);

    void deleteGroup(int groupId, int userId);
	
//	List<Group> getMyGroups(int userId);
//	
//	void addGroupTransaction(int groupId, GroupTransaction transaction);
//	
//	List<GroupTransaction> getGroupTransactions(int groupId);
//	
//	void updateGroupTransaction(int groupId, GroupTransaction transaction);
//	
//	void deleteGroupTransaction(int groupId, int transactionId);
//
//	void addMember(int groupId, int userId, int newMemberId);
//	void removeMember(int groupId, int userId, int targetUserId);
//	List<GroupMemberInfo> getMembers(int groupId, int userId);
}
