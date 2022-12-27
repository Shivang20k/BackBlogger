package com.coding.blog.payloads_dto;



import com.coding.blog.entities.Post;
import com.coding.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
    private int commentId;
	
	private String content;
	

	
	

}
