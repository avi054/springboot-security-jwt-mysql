package edu.aviral.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.aviral.dto.UserDTO;
import edu.aviral.dto.UserRequest;
import edu.aviral.dto.UserResponse;
import edu.aviral.service.UserService;
import edu.aviral.util.JwtUtil;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//benefit of using the UserService as the interface, 
	//if in future user implementation changes, no change in the controller will be required.
	@Autowired
	private UserService userService; // HAS-A

	// Creates and saves a new user in the database : Register a user
	@PostMapping(name = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveUser(@RequestBody UserDTO userDto) {

		Integer id = userService.saveUser(userDto);
		String body = "User '" + id + "'Created and Saved";
		logger.info("{}", body);
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
		
		// Validate the user and return the token (login)
//		Note: In case of form based authenticationManager will be called automatically,
//		but in case of stateless mention as below:
		
		String principal = userRequest.getUsername();
		String credentials = userRequest.getPassword();
		
		Authentication authentication = 
				new UsernamePasswordAuthenticationToken(principal , credentials);
		authenticationManager.authenticate(authentication);
		
		String token = jwtUtil.generateToken(userRequest.getUsername());
		return ResponseEntity.ok(new UserResponse(token, "Token generated successfully!"));
	}
	
}
