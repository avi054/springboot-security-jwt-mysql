package edu.aviral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.aviral.dto.UserRequest;
import edu.aviral.dto.UserResponse;
import edu.aviral.util.JwtUtil;

@RestController
public class AuthenticationController {

	@Autowired
	private JwtUtil jwtUtil;
	
	// Validate the user and return the token (login)
	@PostMapping("/token")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
		
		String token = jwtUtil.generateToken(userRequest.getUsername());
		return ResponseEntity.ok(new UserResponse(token, "Token generated successfully!"));
	}
}
