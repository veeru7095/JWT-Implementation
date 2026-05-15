package com.example.demo.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dtos.RegisterDto;
import com.example.demo.Entity.UserEntity;

import com.example.demo.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public RegisterDto register(RegisterDto registerDto) {
		UserEntity user=modelMapper.map(registerDto, UserEntity.class);
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		UserEntity savedUser=userRepo.save(user);
		return modelMapper.map(savedUser, RegisterDto.class);
	}

}
