package com.coding.blog.security;

import com.coding.blog.payloads_dto.UserDTO;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	private UserDTO user;
}
