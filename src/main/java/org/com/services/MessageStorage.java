package org.com.services;

import java.util.List;

import org.com.model.AllMsg;
import org.com.model.User;

@SuppressWarnings("rawtypes")
public interface MessageStorage {

	void setloginInHM(User user);

	User getloginInHM(User user);

	AllMsg setMsgsToHM(AllMsg message);

	List getAllMsgFromDB(User user);

	List getAllMsgFromDBForSpecificUser(int[] arr);

	String setLogoutInHM(User user);

	List getMsgFromHM(int[] arr);

	Integer[] getActiveFriendsList();

}
