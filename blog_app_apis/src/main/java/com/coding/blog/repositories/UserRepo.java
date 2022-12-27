package com.coding.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.blog.entities.User;
/* Qn) Is Repo is interface then how is @Autowired? Ans)At runtime a Proxy class of Repo is 
 *     created as Repo is an interface
 */
public interface UserRepo extends JpaRepository<User, Integer>{
  Optional<User> findByEmail(String email);
}
