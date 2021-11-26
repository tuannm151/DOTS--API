package com.example.DOTSAPI.validation;

import com.example.DOTSAPI.repository.UserRepo;
import lombok.RequiredArgsConstructor;

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
