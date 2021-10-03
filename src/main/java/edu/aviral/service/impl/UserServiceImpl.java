package edu.aviral.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.aviral.dao.User;
import edu.aviral.dto.UserDTO;
import edu.aviral.repository.UserRepository;
import edu.aviral.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository; //HAS-A
	
	@Override
	public Integer saveUser(UserDTO userDto) {
		// TODO : Password encoding
		User user = new User();
		BeanUtils.copyProperties(userDto, user);  //source, target
		
		User savedUser = userRepository.save(user);
		return savedUser.getId();
	}

}
