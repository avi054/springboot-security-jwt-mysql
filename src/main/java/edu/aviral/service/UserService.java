package edu.aviral.service;

import edu.aviral.dto.UserDTO;

public interface UserService {
	
	Integer saveUser(UserDTO userDto);		//return the primary key
}
