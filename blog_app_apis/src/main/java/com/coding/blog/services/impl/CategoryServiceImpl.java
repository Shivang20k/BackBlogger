package com.coding.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.blog.entities.Category;
import com.coding.blog.exceptions.ResourceNotFoundException;
import com.coding.blog.payloads_dto.CategoryDto;

import com.coding.blog.repositories.CategoryRepo;
import com.coding.blog.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper; //converts one object to another dto to user

	//Create New Category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category = this.dtoToCategory(categoryDto);
		Category saved_category = this.categoryRepo.save(category);
		return this.categoryToDto(saved_category);
	}

	
	//Update Existing User
	
	
	//never update Primary Key in update function.
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
				            .orElseThrow(()->
		                    new ResourceNotFoundException("Category ", "Category ID ", categoryId));
		
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		//category.setCategoryID(categoryDto.getCategoryID()); if use this then update function will not work
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category updated_category = this.categoryRepo.save(category);
		return this.categoryToDto(updated_category);
	}
    
	
	//Delete Existing Category
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
	            .orElseThrow(()->
                new ResourceNotFoundException("Category ", "Category ID ", categoryId));
		this.categoryRepo.delete(category);

	}

	
	// Fetch Category by ID
	@Override
	public CategoryDto getByCategoryId(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
	            .orElseThrow(()->
                new ResourceNotFoundException("Category ", "Category ID ", categoryId));
		return this.categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> catogoriesDTO = categories.stream().map((cat) -> this.categoryToDto(cat)).collect(Collectors.toList());
		return catogoriesDTO;
	}
	
	
	
	//Convert DTO to category
	public Category dtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
	    return category;
	}
	
	//Convert category to DTO
	public CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
	
}
