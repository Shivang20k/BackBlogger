package com.coding.blog.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.blog.entities.Comment;
import com.coding.blog.payloads_dto.ApiResponse;
import com.coding.blog.payloads_dto.CommentDto;
import com.coding.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	//create comment
	@PostMapping ("/posts/{postId}/users/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId , @PathVariable Integer userId )
	{
		CommentDto createdComment = this.commentService.createComment(commentDto, postId , userId );
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
	}
	
	//update comment
	@PutMapping ("/posts/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId)
	{
		CommentDto updatedComment = this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<CommentDto>(updatedComment,HttpStatus.OK);
	}
	
	//delete comment
	@DeleteMapping("/posts/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Comment deleted successfully");
		apiResponse.setSuccess_Status(true);
		
		return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.OK);
		
		
	}
	
	
	//get all comments
	@GetMapping("/posts/comments")
	public ResponseEntity<List<CommentDto>> getAllComments()
	{
		List<CommentDto> cmmnts = this.commentService.getAllComments();
		return new ResponseEntity<List<CommentDto>>(cmmnts, HttpStatus.OK);
				
	}
     
	
	//get single comment by ID
	@GetMapping("/posts/comments/{commentId}")
	public ResponseEntity<CommentDto> getSIngleComment(@PathVariable Integer commentId)
	{
		CommentDto cmmnts = this.commentService.getComment(commentId);
		return new ResponseEntity<CommentDto>(cmmnts, HttpStatus.OK);
				
	}
}
