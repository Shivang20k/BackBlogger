package com.coding.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coding.blog.config.AppConstants;
import com.coding.blog.payloads_dto.ApiResponse;
import com.coding.blog.payloads_dto.PostDto;
import com.coding.blog.payloads_dto.PostResponse;
import com.coding.blog.services.FileService;
import com.coding.blog.services.PostService;


@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	//create
	@PostMapping("/users/{userId}/categories/{categoryId}/posts/")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto , 
			      @PathVariable("userId") Integer userId, 
			      @PathVariable("categoryId") Integer categoryId)
	{
		PostDto createdpost =  this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdpost, HttpStatus.CREATED);
	}
	
	//get post by user
	@GetMapping("/users/{userId}/posts/")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId)
	{
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get post by category
	@GetMapping("/categories/{categoryId}/posts/")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId)
	{
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get all posts
	@GetMapping("/posts/")
	public ResponseEntity<PostResponse> getAllPosts(
		@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
		@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required=false) Integer pageSize,
	    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required=false) String sortBy,
	    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required=false) String sortDir
	)
	{
		PostResponse postsResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postsResponse,HttpStatus.OK);
	}
	
	
	
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable("postId") Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post successfully deleted", true);
	}
	
	//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") Integer postId)
	{
		PostDto updatedpost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedpost, HttpStatus.OK);
		
	}
	
	
	//get by keyword
	
	
	/* using /search/ also in URL bcoz if directly passed string after /posts/ then 
	//computer thinks that it is An integer PostID as declared in other controller. 
	so to differentiate using posts/search/keyword*/
	
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords)
	{
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	

	
	//get post by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId)
	{
		PostDto post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
			
	}
	
	//post-image upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage ( 
			@RequestParam("image") MultipartFile image, 
			@PathVariable("postId") Integer postId       ) throws IOException
	{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto> (updatedPostDto,HttpStatus.OK);
	}
	
	//return post image or serve image
	
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
