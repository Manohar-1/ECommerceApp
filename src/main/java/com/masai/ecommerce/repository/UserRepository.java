package com.masai.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.ecommerce.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 
    User findByUsername(String username);
}
