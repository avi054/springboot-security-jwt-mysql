package edu.aviral.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired //UserServiceImpl bean will be injected.
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private InvalidUserAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//for Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// values are set into the Authentication object.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	// for Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable() //required
			.authorizeRequests()
			.antMatchers("/api/v1/users", "/api/v1/users/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)//triggered if authentication failed
			.and()
			.sessionManagement()//since we are doing stateless programming, disable session management.
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//register the filter
			.and()
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
			;
	}
	
	@Autowired
	private SecurityFilter securityFilter;
	
}
