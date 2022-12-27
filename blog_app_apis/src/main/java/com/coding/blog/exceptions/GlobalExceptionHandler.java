package com.coding.blog.exceptions;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.coding.blog.payloads_dto.ApiResponse;
import com.coding.blog.security.ApiSecurityException;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	//If given ID does not exist in updating, finding specific user and deleting user
	// message in exceptions/ResourceNotFoundException.java
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException object)
	{
		String message = object.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	
	//Handle Exception when invalid fields given while creating or updating user
	//Messages are in UserDTO
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException object)
	{
		Map<String,String> response = new HashMap<>();
		object.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	// Handle Exception no UserID passed with delete operation
	// SelfMade
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException object)
	{
		String message = "Please pass UserID of the user to delete. UserID can not be Empty";
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.PARTIAL_CONTENT);
	}
	
	
	
	//JWT SECURITY EXCEPTION HANDLER
	@ExceptionHandler(ApiSecurityException.class)
	public ResponseEntity<ApiResponse> ApiSecurityExceptionHandler(ApiSecurityException object)
	{
		String message = object.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
	

}
