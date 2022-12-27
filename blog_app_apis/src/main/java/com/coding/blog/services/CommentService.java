package com.coding.blog.services;

import java.util.List;

import com.coding.blog.payloads_dto.CommentDto;

public interface CommentService {
	
	//create
	CommentDto createComment(CommentDto commentdto, Integer postId , Integer userId );
	
	//delete
	void deleteComment(Integer commentId);
	
	//update
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
	
	//get all
	List<CommentDto> getAllComments();
	
	//get by comment id
	CommentDto getComment(Integer commentId);

}
