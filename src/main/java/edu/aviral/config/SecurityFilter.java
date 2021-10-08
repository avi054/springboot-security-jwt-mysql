package edu.aviral.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.aviral.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetilsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1. read token from the request Authorization header

		String token = request.getHeader("Authorization");

		if (token != null) {
			// do validation
			String userName = jwtUtil.getUserName(token);

			// userName should not be empty and
			// context-authentication must be empty
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetilsService.loadUserByUsername(userName);

				// validate token
				boolean isValid = jwtUtil.validateToken(token, userDetails.getUsername());

				if (isValid) { // create the context
					UsernamePasswordAuthenticationToken authToken 
					= new UsernamePasswordAuthenticationToken(userName,
							userDetails.getPassword(), userDetails.getAuthorities());

					// linking token with the current object.
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// final object stored in SecurityContext with user details(uname,pass)
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
