package com.example.DOTSAPI.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.DOTSAPI.exception.ExceptionUtils;
import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.configuration.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.DOTSAPI.configuration.SecurityConstants.EXPIRATION_TIME;
import static com.example.DOTSAPI.configuration.SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
           try {
               AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
               log.info("Username: {}\nPassword: {}", appUser.getUserName(), appUser.getPassword());
               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser.getUserName(),appUser.getPassword());
               return authenticationManager.authenticate(authenticationToken);
           } catch(BadCredentialsException e) {
               System.out.println("AAAA");
               List<String> details = List.of("Username or password is incorrect");
               ExceptionUtils.raiseErrorJson("Authentication failed", details, HttpStatus.UNAUTHORIZED, request, response);
               return null;
           } catch(Exception e) {
               List<String> details = List.of("Invalid or missing token");
               ExceptionUtils.raiseErrorJson("Authentication failed", details, HttpStatus.UNAUTHORIZED, request, response);
               return null;
           }
    }

    public static String generateToken(List<String> claims, String issuer, String subject, Algorithm algorithm) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuer(issuer)
                .withClaim("roles", claims)
                .sign(algorithm);
    }

    String generateRefreshToken(List<String> claims, String issuer, String subject,
                                Algorithm algorithm) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .withIssuer(issuer)
                .withClaim("roles", claims)
                .sign(algorithm);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET.getBytes());
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String access_token = generateToken(roles, request.getRequestURL().toString(), user.getUsername(),
                algorithm);

        String refresh_token = generateRefreshToken(roles, request.getRequestURL().toString(), user.getUsername(),
                algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
