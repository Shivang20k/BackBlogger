package com.coding.blog.services.impl;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.coding.blog.entities.Category;
import com.coding.blog.entities.Post;
import com.coding.blog.entities.User;
import com.coding.blog.exceptions.ResourceNotFoundException;
import com.coding.blog.payloads_dto.PostDto;
import com.coding.blog.payloads_dto.PostResponse;
import com.coding.blog.repositories.CategoryRepo;
import com.coding.blog.repositories.PostRepo;
import com.coding.blog.repositories.UserRepo;
import com.coding.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		
		
		User user = this.userRepo.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		Category category = this.categoryRepo.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));
				
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdpost = this.postRepo.save(post);
		return this.modelMapper.map(createdpost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post ID ", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedpost = this.postRepo.save(post);
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post ID ", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		
		
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		               // OR //
		/*
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
        {
			sort=Sort.by(sortBy).ascending();
        }
		else
		{
			sort=Sort.by(sortBy).descending();
		}
		*/
		
		Pageable paged = PageRequest.of(pageNumber, pageSize, sort); 
				
		Page<Post> pagedposts = this.postRepo.findAll(paged);
		List<Post> allposts = pagedposts.getContent();
		
		List<PostDto> allpostsDtos = allposts.stream().map((p) -> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		
		
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(allpostsDtos);
		postResponse.setPageNumber(pagedposts.getNumber());
		postResponse.setPageSize(pagedposts.getSize());
		postResponse.setTotalElements(pagedposts.getTotalElements());
		postResponse.setTotalPages(pagedposts.getTotalPages());
		postResponse.setLastPage(pagedposts.isLast());
		
		
		
		return postResponse;
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post ID ", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		//check if given category exists or not
		Category category = this.categoryRepo.findById(categoryId)
	            .orElseThrow(()->
                new ResourceNotFoundException("Category ", "Category ID ", categoryId));

		//fetching post of given category
		List<Post> posts=this.postRepo.findByCategory(category);
		
		//convert lists of posts into postDto
		List<PostDto> postsDtos = posts.stream().map((p) -> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postsDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		//check if given user exists or not
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		//fetching posts of given user
		List<Post> posts = this.postRepo.findByUser(user);
		
		//convert list of post to postDtos
		List<PostDto> postsDtos = posts.stream().map((p) -> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postsDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> posts = this.postRepo.findByTitle("%"+keyword+"%");
		               //or
		//List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		
		
		List<PostDto> postsDtos = posts.stream().map((p) -> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postsDtos;
	}

}
