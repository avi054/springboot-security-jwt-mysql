package edu.aviral.service;

import java.util.Optional;

import edu.aviral.dao.User;
import edu.aviral.dto.UserDTO;

public interface UserService {
	
	Integer saveUser(UserDTO userDto);		//return the primary key
	Optional<User> findByUsername(String username);

}
