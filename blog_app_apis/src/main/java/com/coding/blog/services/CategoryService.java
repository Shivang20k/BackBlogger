package com.coding.blog.services;

import java.util.List;

import com.coding.blog.payloads_dto.CategoryDto;


public interface CategoryService {
	//create
		CategoryDto createCategory(CategoryDto categoryDto);
	//update
		CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	//delete
		void deleteCategory(Integer categoryId);
	// Get one
		CategoryDto getByCategoryId(Integer categoryId);
	//Get all
		List<CategoryDto> getAllCategories();
		
}
