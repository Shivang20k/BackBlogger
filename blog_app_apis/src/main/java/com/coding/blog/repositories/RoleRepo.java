package com.coding.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
