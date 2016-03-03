package org.com.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.com.model.AllMsg;
import org.com.model.User;
import org.com.services.MessageStorageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@SuppressWarnings("rawtypes")
@Controller
public class MessageController {

	private static final Logger logger = Logger.getLogger(MessageController.class);
	
	@Autowired
	MessageStorageImpl msgStore;
	
	@RequestMapping(value="/sendMsgToHashSet",method=RequestMethod.POST)
	public @ResponseBody
	AllMsg sendMsgToHashSet(@RequestBody AllMsg message){
		logger.info("Request with AllMsg object to store in haspMap");
		return msgStore.setMsgsToHM(message);
	}
	
	@RequestMapping(value="/getMyMsgsForFirstTime",method=RequestMethod.POST)
	public @ResponseBody
	List getMyMsgsForFirstTime(@RequestBody User user){
		logger.info("Request with AllMsg object to store in haspMap");
		return msgStore.getAllMsgFromDB(user);
	}
	
	@RequestMapping(value="/startChatBox",method=RequestMethod.POST)
	public @ResponseBody
	List startChatBox(@RequestBody int[] arr){
		logger.info("Get all msg from user : "+arr[1]+" to user : "+arr[0]);
		return msgStore.getAllMsgFromDBForSpecificUser(arr);
	}
	
	@RequestMapping(value="/getMsgOnInterval",method=RequestMethod.POST)
	public @ResponseBody
	List getMsgOnInterval(@RequestBody int[] arr){
		logger.info("Get msgs on interval for user id : "+arr[0]);
		return msgStore.getMsgFromHM(arr);
	}
}
