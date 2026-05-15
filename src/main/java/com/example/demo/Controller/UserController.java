package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dtos.RegisterDto;

import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterDto> register(@RequestBody RegisterDto registerDto){
		return new ResponseEntity<RegisterDto>(userService.register(registerDto),HttpStatus.CREATED);
	}
	
	 @GetMapping("/hello")
	    public String hello() {

	        return "JWT Authentication Success";
	    }

}
