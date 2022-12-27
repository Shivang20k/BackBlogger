package com.coding.blog.payloads_dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.coding.blog.entities.Category;
import com.coding.blog.entities.Comment;
import com.coding.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	//do not need to pass in postman as default set in PostServiceImpl
	private String imageName;
    //private String imageName="default.png"; we will fetch directly imageName from directory
	
	//do not need to pass in postman as getting live date
	private Date addedDate;
	
	
	//getting userid and category id value from url directly so
	//do not need to pass in postman
	private CategoryDto category;
	
	
	//do not need to pass in postman
	private UserDTO user;
	
	//to get comments of post
	private Set<CommentDto> comments = new HashSet<>();
	
}
