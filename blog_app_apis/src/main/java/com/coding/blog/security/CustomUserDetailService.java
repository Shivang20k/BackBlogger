package com.coding.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coding.blog.entities.User;
import com.coding.blog.exceptions.ResourceNotFoundException;
import com.coding.blog.repositories.UserRepo;


//UserDetailsService loads user specific data
@Service
public class CustomUserDetailService implements UserDetailsService{
	
    @Autowired
    private UserRepo userRepo;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User ","Email :"+username, 0));
	    return user;
	
	}

}
