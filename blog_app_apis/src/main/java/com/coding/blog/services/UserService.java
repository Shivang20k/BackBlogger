package com.coding.blog.services;

import java.util.List;

import com.coding.blog.payloads_dto.UserDTO;


public interface UserService {
	
	//Registering a user after jwt
	
	UserDTO registerNewUser(UserDTO user);
	
	//////////////////////////////////
	
	UserDTO createUser(UserDTO user);
	UserDTO updateUser(UserDTO user, Integer userId);
	UserDTO getUserById(Integer userID);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);

}
