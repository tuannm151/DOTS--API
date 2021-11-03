package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String userName);
}
