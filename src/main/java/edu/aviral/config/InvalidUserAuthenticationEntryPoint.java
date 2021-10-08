package edu.aviral.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class InvalidUserAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// Generally in case form based, we send a login denied page, but here we
		// are dealing with REST
		// This class will return Message for unAuthenticated user.
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"You are not Authorized to access this page!!!");
	}

}
