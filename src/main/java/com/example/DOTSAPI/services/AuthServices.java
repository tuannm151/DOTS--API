package com.example.DOTSAPI.services;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.DOTSAPI.exception.CustomAuthenticationException;
import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import static com.example.DOTSAPI.configuration.SecurityConstants.SECRET;
import static com.example.DOTSAPI.filter.CustomAuthorizationFilter.decodeJWT;

@Service
@RequiredArgsConstructor
public class AuthServices {
    private final UserRepo userRepo;
    public AppUser loadUsernameByToken(String token) throws CustomAuthenticationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
            DecodedJWT decodedJWT = decodeJWT(token,algorithm);
            String userName =  decodedJWT.getSubject();
           return userRepo.findByUserName(userName);
        } catch (Exception e) {
            throw new CustomAuthenticationException("Token invalid or expired");
        }
    }
}
