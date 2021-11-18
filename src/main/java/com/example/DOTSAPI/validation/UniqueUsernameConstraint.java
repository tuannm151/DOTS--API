package com.example.DOTSAPI.validation;

import com.example.DOTSAPI.repository.UserRepo;
import com.example.DOTSAPI.services.AppUserServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@RequiredArgsConstructor
public class UniqueUsernameConstraint implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepo userRepo;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        System.out.println(userRepo);
        return !userRepo.existsByUserName(userName);
    }
}
