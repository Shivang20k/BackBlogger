package com.coding.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.coding.blog.exceptions.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coding.blog.entities.Role;
import com.coding.blog.entities.User;
import com.coding.blog.payloads_dto.UserDTO;
import com.coding.blog.repositories.RoleRepo;
import com.coding.blog.repositories.UserRepo;
import com.coding.blog.services.UserService;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
	private UserRepo userRepo;
    
    @Autowired
    private ModelMapper modelMapper; //converts one object to another dto to user
	
	
	//Create New User
    @Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = this.dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);
	
		return this.userToDto(savedUser);
	}
    
    //Update Existing User
	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setAbout(userDTO.getAbout());
		user.setPassword(userDTO.getPassword());
		
		User updateUser = this.userRepo.save(user);
		
		UserDTO userDTo1 = this.userToDto(updateUser);
		 
		return userDTo1;
	}

	
	//Get single user
	@Override
	public UserDTO getUserById(Integer userId) {
		
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		UserDTO userDTo1 = this.userToDto(user);
		 
		return userDTo1;
		
		
	}

	//Get all users
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDTOs = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDTOs;
	}
	
	
    //Delete existing User
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		this.userRepo.delete(user);

	}
	
	
	/////////////////register new user after jwt video//////////////////////
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDTO registerNewUser(UserDTO userDTO)
	{
		User user = this.modelMapper.map(userDTO, User.class);
		
		//encoding password
		user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
		
		//roles
		Role role = this.roleRepo.findById(2).get(); //NORMAL ROLE for 2
		user.getRole().add(role);
		
		
		User newUser = this.userRepo.save(user);
		
		UserDTO created_userdto = this.modelMapper.map(newUser, UserDTO.class);
		created_userdto.getRole().add(role);
		        
		return created_userdto;
		
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////
	
	
	// old way before modelMapper was used //
	
	
	//Convert DTO to User
	public User dtoToUser(UserDTO userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	     //OR
	/*
	public User dtoToUser(UserDTO userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		return user;
	}
	*/
	
	//Convert User to DTO
	
	public UserDTO userToDto(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		return userDTO;
	}
	/*
	public UserDTO userToDto(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setAbout(user.getAbout());
		userDTO.setEmail(user.getEmail());
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setPassword(user.getPassword());
		return userDTO;
	}
	*/
	

}
