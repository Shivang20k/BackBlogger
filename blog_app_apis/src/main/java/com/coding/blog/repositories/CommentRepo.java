package com.coding.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
