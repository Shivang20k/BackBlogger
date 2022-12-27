package com.coding.blog.services.impl;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.blog.entities.Comment;
import com.coding.blog.entities.Post;
import com.coding.blog.entities.User;
import com.coding.blog.exceptions.ResourceNotFoundException;
import com.coding.blog.payloads_dto.CommentDto;
import com.coding.blog.payloads_dto.PostDto;
import com.coding.blog.repositories.CommentRepo;
import com.coding.blog.repositories.PostRepo;
import com.coding.blog.repositories.UserRepo;
import com.coding.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public CommentDto createComment(CommentDto commentdto, Integer postId , Integer userId ) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post ID ", postId));
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User ID ", userId));
		
	    Comment comments = this.modelMapper.map(commentdto, Comment.class);
	    comments.setPost(post);
	    comments.setUser(user);
	    
	    Comment savedComment =  this.commentRepo.save(comments);
	    
	    return this.modelMapper.map(savedComment, CommentDto.class);
	    
	    
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow( () -> new ResourceNotFoundException("Comment ", "Comment ID ", commentId));
		this.commentRepo.delete(comment);

	}

	@Override
	public CommentDto updateComment(CommentDto commentdto, Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow( () -> new ResourceNotFoundException("Comment ", "Comment ID ", commentId));
		
		
	    comment.setContent(commentdto.getContent());
	    
	    
	    
	    Comment savedComment =  this.commentRepo.save(comment);
	    
	    return this.modelMapper.map(savedComment, CommentDto.class);
	    
	    
	}
	
	@Override
	public List<CommentDto> getAllComments(){
		List<Comment> cmnts =this.commentRepo.findAll();
		
		List<CommentDto> cmntsDtos = cmnts.stream().map((c) -> this.modelMapper.map(c, CommentDto.class)).collect(Collectors.toList());
		return cmntsDtos;
	}
	
	
	@Override
	public CommentDto getComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow( () -> new ResourceNotFoundException("Comment ", "Comment ID ", commentId));
		
		return this.modelMapper.map(comment, CommentDto.class);
		
	}

}
