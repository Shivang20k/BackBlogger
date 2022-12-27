package com.coding.blog.exceptions;


import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	String resourceName;
	String fieldname;
	long fieldvalue;
	
	public ResourceNotFoundException(String resourceName, String fieldname, long fieldvalue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldname,fieldvalue));
		this.resourceName = resourceName;
		this.fieldname = fieldname;
		this.fieldvalue = fieldvalue;
	}

}
