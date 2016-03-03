package org.com.services;

import java.util.List;

import org.com.model.Friends;
import org.com.model.User;

@SuppressWarnings("rawtypes")
public interface FriendsDetails {
	List getFriendsList(User user);
	User getUserDetails(int id);
	String setFriendRequest(Friends friends);
	List getUsersList(String[] key);
	int getFriendRequest(User user);
	List getFriendRequestList(User user);
	boolean setFriendRequestAccepted(int[] arr);
}
