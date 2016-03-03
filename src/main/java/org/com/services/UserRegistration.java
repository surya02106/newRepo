package org.com.services;

import org.com.model.User;

public interface UserRegistration {
	User userRegister(User user);
	String userValidation(User user);
	String checkForUser(User user);
}
