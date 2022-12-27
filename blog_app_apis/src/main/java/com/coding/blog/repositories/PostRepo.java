package com.coding.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coding.blog.entities.Category;
import com.coding.blog.entities.Post;
import com.coding.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	//creating custom methods 
	
	
	
	//findBy___ is a custom extra method already in JPA so no need to write extra query
	List<Post> findByUser(User user);
	
	//findBy___ is a custom extra method already in JPA so no need to write extra query
	List<Post> findByCategory(Category category);
	
	//for table write just table name with First letter Capital and no need to write DB name
	@Query("select p from Post p where p.title like :key") //key =%keyword%
	List<Post> findByTitle(@Param("key") String title);
	              //or//
	List<Post> findByTitleContaining(String title);
	
	

}
