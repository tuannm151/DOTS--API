package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User getByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
