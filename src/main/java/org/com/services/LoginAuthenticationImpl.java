package org.com.services;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.com.dao.BaseDao;
import org.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("rawtypes")
public class LoginAuthenticationImpl implements LoginAuthentication{
	private static final Logger logger = Logger.getLogger(LoginAuthentication.class);

	@Autowired
	BaseDao baseDao;
	
	@Autowired
	MessageStorage msgStore;
	
	@Override
	public User authenticateuser(User user) {
		logger.info("'username' and 'password' from UserLoginController -> userLoginAuthentication()");
		Iterator itr = baseDao.setQuery("From User where username='"+user.getUsername()+"' AND password='"+user.getPassword()+"'").iterator();
		if(itr.hasNext()){
			user=(User)itr.next();
			msgStore.setloginInHM(user);
			logger.info("User Found");
		}
		return user;
	}
}
