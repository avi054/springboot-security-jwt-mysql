package edu.aviral.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.aviral.dao.User;
import edu.aviral.dto.UserDTO;
import edu.aviral.repository.UserRepository;
import edu.aviral.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository; //HAS-A
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Override
	public Integer saveUser(UserDTO userDto) {
		
		User user = new User();
		//Password encoding
		userDto.setPassword(pwdEncoder.encode(userDto.getPassword()));
		BeanUtils.copyProperties(userDto, user);  //source, target
		
		User savedUser = userRepository.save(user);
		return savedUser.getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> dbUser = findByUsername(username);
//		if(!dbUser.isPresent()) {
//			throw new UsernameNotFoundException("User not found!");
//		}
		
		// Reading user from the database.
		User user = findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User not found!"));
		
		Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
								.map(role -> new SimpleGrantedAuthority(role))
								.collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails
				.User(user.getUsername(), user.getPassword(), authorities);
	}

}
