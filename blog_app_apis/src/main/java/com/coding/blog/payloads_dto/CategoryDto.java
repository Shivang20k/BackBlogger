package com.coding.blog.payloads_dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private int categoryID;
	
	
	@NotNull
	@Size(min = 2, message = "CategoryTitle must be greater than 2 characters")
	@NotEmpty
	private String categoryTitle;
	

	@NotNull
	@Size(min = 4, max = 25,message = "Description must be in range of 6 and 25")
	@NotEmpty
	private String categoryDescription;

}
