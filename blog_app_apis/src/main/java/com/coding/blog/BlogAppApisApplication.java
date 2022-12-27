package com.coding.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coding.blog.entities.Role;
import com.coding.blog.repositories.RoleRepo;
import com.coding.blog.repositories.UserRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) 
	{
		SpringApplication.run(BlogAppApisApplication.class, args);
	}


    @Bean
    ModelMapper modelMapper()
    {
        return new ModelMapper();
    }


 // making role 1st time////////////////////
	
 	@Autowired
 	private RoleRepo roleRepo; 
 	
 	
	@Override
	public void run(String... args) throws Exception {
		 
		//System.out.println(this.passwordEncoder.encode("spider"));
		
		
		try {
			Role role_admin = new Role();
			role_admin.setId(1);
			role_admin.setRole_name("ROLE_ADMIN");
			
			Role role_normal = new Role();
			role_normal.setId(2);
			role_normal.setRole_name("ROLE_NORMAL");
			
			List<Role> lst = List.of(role_admin, role_normal);
			List<Role> result = this.roleRepo.saveAll(lst);
			
			result.forEach( r -> {System.out.println( r.getRole_name() );} );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

}
