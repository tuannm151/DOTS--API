package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
