package com.coding.blog.payloads_dto;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.propertyeditors.URLEditor;

import com.coding.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
	
	private int id;
	
	@NotNull
	@Size(min = 2, message = "Username must be greater than 2 characters")
	@NotEmpty
	private String name;
	
	@Email(message = "Enter a valid Email Address")
	private String email;
	
	
	@NotNull
	@Size(min = 6, max = 12,message = "Password must be in range of 6 and 12")
	//@JsonIgnore                 //To Hide Password, but it Will ignore for both GET(Marshalling) and POST(UnMarshalling)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)        //To hide password only for Marshalling
	private String password;
	
	
	@Size(max = 25)
	private String about;
	
	// to get comments made by user
	private Set<CommentDto> comments = new HashSet<>();
	
   
	
	
	//to get role of user
	private Set<Role> role = new HashSet<>();
	

}
