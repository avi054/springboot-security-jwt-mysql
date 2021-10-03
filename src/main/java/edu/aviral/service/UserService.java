package edu.aviral.service;

import edu.aviral.model.User;

public interface UserService {
	
	Integer saveUser(User user);		//return the primary key
}
