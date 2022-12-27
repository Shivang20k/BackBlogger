package com.coding.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.blog.payloads_dto.ApiResponse;
import com.coding.blog.payloads_dto.UserDTO;
import com.coding.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	//POST-create user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO)
	{
		UserDTO createduserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<>(createduserDTO, HttpStatus.CREATED);
	}
	//PUT-update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,
			@PathVariable("userId") Integer uid)
	{
		UserDTO updateduserDTO = this.userService.updateUser(userDTO, uid);
		return new ResponseEntity<>(updateduserDTO, HttpStatus.ACCEPTED);
		/*return ResponseEntity.ok(updateduserDTO);*/
		
		
	}
	
	//Suppose only admin can delete user
	//DELETE-delete user
	
	@PreAuthorize("hasRole('ADMIN')") // only admin access this api not any normal user
	@SuppressWarnings("unused")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid )
	{
		String message = " User Deleted Successfully";
		this.userService.deleteUser(uid);
		/*return ResponseEntity.ok(message);*/
		/* OR */
		/*return ResponseEntity(Map.of("message", "User Deleted Successfully"),HttpStatus.OK);*/
		/* OR */
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	
	//GET- get single user
	@GetMapping("/{userId}")
	public  ResponseEntity<UserDTO> getUserById(@Valid @PathVariable("userId") Integer uid)
	{
		UserDTO updateduserDTO = this.userService.getUserById(uid);
		return new ResponseEntity<>(updateduserDTO, HttpStatus.ACCEPTED);
	}
	//GET- get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUser()
	{
		List<UserDTO> fetcheduserDTOs = this.userService.getAllUsers();
		return new ResponseEntity<>(fetcheduserDTOs, HttpStatus.ACCEPTED);
	}

}
