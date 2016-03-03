package org.com.controller;

import org.apache.log4j.Logger;
import org.com.model.User;
import org.com.services.LoginAuthentication;
import org.com.services.MessageStorage;
import org.com.services.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
public class UserLoginController {
	
	private static final Logger logger = Logger.getLogger(UserLoginController.class);
	
	@Autowired
	LoginAuthentication login;
	
	@Autowired
	UserRegistration register;
	
	@Autowired
	MessageStorage msgStore;
	
	//User Login
	@RequestMapping(value="/userLoginAuthentication",method=RequestMethod.POST)
	public @ResponseBody
	User userLoginAuthentication(@RequestBody User user){
		logger.info("Request From 'index.html'. In userLoginAuthentication()");
		return login.authenticateuser(user);
	}
	
	
	//User Logout
	@RequestMapping(value="/userLogout",method=RequestMethod.POST)
	public @ResponseBody
	String userLogout(@RequestBody User user){
		logger.info("Logout for : "+user.toString());
		return msgStore.setLogoutInHM(user);
	}
	
	
	//User Registration
	@RequestMapping(value="/userRegistration",method=RequestMethod.POST)
	public @ResponseBody
	String userRegistration(@RequestBody User user){
		String result=register.checkForUser(user);
		if(result.equals("000")){
			result=register.userValidation(user);
			if(result.equals("000")){
				user=register.userRegister(user);
				result=Integer.toString(user.getId());
			}
		}
		return result;
	}
	
	@RequestMapping(value="/emailVerification",method=RequestMethod.POST)
	public @ResponseBody
	String emailVerification(@RequestBody User user){
		logger.info("Verifying for email : "+user.toString());
		if(user.getEmailstatus().equals("1"))
			return "000";
		else{
			return "Email is Not Verified";
		}
	}
}
