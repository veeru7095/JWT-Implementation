package com.example.demo.Util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtility {
	
	private final String secret="my_secret_key_my_secret_key_my_secret_key_my_secret_key";
	
	private final SecretKey key=Keys.hmacShaKeyFor(secret.getBytes());
	
	public String generateToken(String email) {		
		return Jwts.builder()
		  .setSubject(email)
		  .setIssuedAt(new Date())
		  .setExpiration(new Date(System.currentTimeMillis() + 10000*60*60))
		  .signWith(key, SignatureAlgorithm.HS256)
		  .compact();
		
	}
	
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
		.setSigningKey(key)
		.build()
		.parseClaimsJws(token)
		.getBody();
	}
	
	public String extractEmail(String token) {
		Claims body=extractClaims(token);
		return body.getSubject();
	}
	
	public boolean isExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}
	
	public boolean validate(String email, UserDetails userDetails, String token) {
		return email.equals(userDetails.getUsername()) && !isExpired(token);
	}

}
