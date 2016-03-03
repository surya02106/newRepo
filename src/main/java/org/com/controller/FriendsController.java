package org.com.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.com.model.Friends;
import org.com.model.User;
import org.com.services.FriendsDetails;
import org.com.services.MessageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings("rawtypes")
@Controller
public class FriendsController {
	private static final Logger logger = Logger.getLogger(FriendsController.class);
	
	@Autowired
	FriendsDetails friend;
	
	@Autowired
	MessageStorage msgStore;
	
	//Get Friends List
	@RequestMapping(value="/getFriendsList",method=RequestMethod.POST)
	public @ResponseBody
	List getFriendsList(@RequestBody User user){
		logger.info("Request From 'home.html'. In getFriendsList() by : "+user.getFirstname()+" "+user.getLastname());
		return friend.getFriendsList(user);
	}
	
	//Search Friends List
	@RequestMapping(value="/getUsersList",method=RequestMethod.POST)
	public @ResponseBody
	List getUsersList(@RequestBody String[] ke){
		logger.info("Request From 'home.html'. In getUsersList()");
		return friend.getUsersList(ke);
	}
	
	//Search Friends List
	@RequestMapping(value="/getUsersDetails",method=RequestMethod.POST)
	public @ResponseBody
	User getUsersDetails(@RequestBody int id){
		logger.info("Request From 'home.html'. In getUserDetails()");
		return friend.getUserDetails(id);
	}
	
	//Search Friends List
	@RequestMapping(value="/sendFriendRequest",method=RequestMethod.POST)
	public @ResponseBody
	String sendFriendRequest(@RequestBody Friends friends){
		logger.info("Request From 'home.html'. In sendFriendRequest()");
		return friend.setFriendRequest(friends);
	}
	
	//Friend Request Count
	@RequestMapping(value="/getFriendsRequestCount",method=RequestMethod.POST)
	public @ResponseBody
	int getFriendsRequestCount(@RequestBody User user){		
		logger.info("Request From 'home.html'. In getFriendsRequestCount()");
		return friend.getFriendRequest(user);
	}
	
	//Friend Request List
	@RequestMapping(value="/getFriendsRequestList",method=RequestMethod.POST)
	public @ResponseBody
	List getFriendsRequestList(@RequestBody User user){		
		logger.info("Request From 'home.html'. In getFriendsRequestList()");
		return friend.getFriendRequestList(user);
	}
	
	@RequestMapping(value="/setAcceptFriendsRequest",method=RequestMethod.POST)
	public @ResponseBody
	String setAcceptFriendsRequest(@RequestBody int[] arr){		
		logger.info("Request From 'home.html'. In setAcceptFriendsRequest()");
		if(friend.setFriendRequestAccepted(arr))
			return "000";
		else
			return "111";
	}
	
	@RequestMapping(value="/getActiveFriendsList",method=RequestMethod.POST)
	public @ResponseBody
	Integer[] getActiveFriendsList(){		
		logger.info("Request From 'home.html'. In getActiveFriendsList()");
		return msgStore.getActiveFriendsList();
	}
}