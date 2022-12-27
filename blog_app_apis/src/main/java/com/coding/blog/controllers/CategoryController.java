package com.coding.blog.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.coding.blog.payloads_dto.ApiResponse;
import com.coding.blog.payloads_dto.CategoryDto;

import com.coding.blog.services.CategoryService;


@RequestMapping("/api/categories")
@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	//create
	
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
	}
	
	// not working
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory( @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer cid)
	{
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, cid);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	
	//delete
	@SuppressWarnings("unused")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cid )
	{
		String message = " Category Deleted Successfully";
		this.categoryService.deleteCategory(cid);
		/*return ResponseEntity.ok(message);*/
		/* OR */
		/*return ResponseEntity(Map.of("message", "Category Deleted Successfully"),HttpStatus.OK);*/
		/* OR */
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true),HttpStatus.OK);
	}
	
	
	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId)
	{
		CategoryDto categoryDto = this.categoryService.getByCategoryId(catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}
	
	
	//getall
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> fetchedcategoryDTOs = this.categoryService.getAllCategories();
		return new ResponseEntity<>(fetchedcategoryDTOs, HttpStatus.ACCEPTED);
	}

}
