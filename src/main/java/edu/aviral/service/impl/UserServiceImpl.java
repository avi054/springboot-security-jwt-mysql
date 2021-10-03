package edu.aviral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.aviral.model.User;
import edu.aviral.repository.UserRepository;
import edu.aviral.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository; //HAS-A
	
	@Override
	public Integer saveUser(User user) {
		// TODO : Password encoding
		User savedUser = userRepository.save(user);
		return savedUser.getId();
	}

}
