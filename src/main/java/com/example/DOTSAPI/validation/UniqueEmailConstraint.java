package com.example.DOTSAPI.validation;

import com.example.DOTSAPI.repository.UserRepo;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailConstraint implements ConstraintValidator<UniqueEmail, String> {
    private final UserRepo userRepo;


    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        System.out.println(userRepo);
        return !userRepo.existsByEmail(email);
    }
}
