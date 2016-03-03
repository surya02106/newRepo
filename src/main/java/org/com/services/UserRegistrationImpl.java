package org.com.services;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.com.dao.BaseDao;
import org.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;


@SuppressWarnings("rawtypes")
public class UserRegistrationImpl implements UserRegistration{

	@Autowired
	BaseDao baseDao;
	
	private Matcher matcher;
	private Pattern pat;
	static final String NAMEP="^[a-zA-Z]{3,20}";
	static final String EMAILP="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	static final String MOBILEP="^0?[789]{1}\\d{9}$";
	static final String PASSWORDP="^[a-zA-Z0-9]{6,20}";
	
	@Override
	public User userRegister(User user) {
		baseDao.saveOrUpdate(user); 
		return user;
	}
	
	@Override
	public String userValidation(User user){
		if(user.getFirstname()==null || !matchPattern(NAMEP,user.getFirstname())){
			return "Please Provide Valid First Name";
		}
		if(user.getLastname()==null || !matchPattern(NAMEP,user.getLastname())){
			return "Please Provide Valid Last Name";
		}
		if(user.getEmail()==null || !matchPattern(EMAILP,user.getEmail())){
			return "Please Provide Valid Email Id";
		}
		if(user.getUsername()==null || !matchPattern(NAMEP,user.getUsername())){
			return "Please Provide Valid Username Name";
		}
		if(user.getPassword()==null || !matchPattern(PASSWORDP,user.getPassword())){
			return "Password is Invalid";
		}
		return "000";
	}
	
	private boolean matchPattern(String pattern, String field){
		pat = Pattern.compile(pattern);
		matcher = pat.matcher(field);
		return matcher.matches();
	}


	@Override
	public String checkForUser(User user) {
		Iterator itr = baseDao.setQuery("From User where email='"+user.getEmail()+"'").iterator();
		String result="000";
		if(itr.hasNext()){
			result="Email Id Already Exist";
		}else{
			itr = baseDao.setQuery("From User where username='"+user.getUsername()+"'").iterator();
			if(itr.hasNext()){
				result="Username Already Exist";
			}
		}
		return result;
	}
}
