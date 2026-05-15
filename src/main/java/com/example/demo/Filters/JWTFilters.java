package com.example.demo.Filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Util.JWTUtility;
import com.example.demo.security.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilters extends OncePerRequestFilter{

	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private UserDetailsService detailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		
		String token=null;
		String email = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token=authHeader.substring(7);	
			email=jwtUtility.extractEmail(token);
		}
		
		if(email !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails=detailsService.loadUserByUsername(email);
			if(jwtUtility.validate(email, userDetails, token)) {
				UsernamePasswordAuthenticationToken authToken=
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			
			}
		}
		filterChain.doFilter(request, response);
	}

}
