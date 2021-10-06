package edu.aviral.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.issuer}")
	private String issuer;

	//1. Generate Token
	public String generateToken(String subject) { //userName can be used as the subject
		
		return Jwts.builder()
					.setSubject(subject)
					.setIssuer(issuer)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()
							+ TimeUnit.MINUTES.toMillis(15)))
					.signWith(SignatureAlgorithm.HS256, secret)
					.compact();
	}
	
	//2. Read Claims
	public Claims getClaims(String token) {
		
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}
	
	//3. read Exp Date
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	
	//4. get subject/userName
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}
	
	//5. Validate expiration date
	public boolean isTokenExp(String token) {
		 Date expDate = getExpDate(token);
		 return expDate.before(new Date(System.currentTimeMillis()));
	}
	
	// 6. validate userName and expDate
	public boolean validateToken(String token, String userName) {
		String tokenUsername = getUserName(token);
		return (userName.equals(tokenUsername) && !isTokenExp(token));
	}
	
}
