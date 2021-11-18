package com.example.DOTSAPI.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.DOTSAPI.exception.ExceptionUtils;
import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.model.Role;
import com.example.DOTSAPI.services.AppUserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.DOTSAPI.filter.CustomAuthenticationFilter.generateToken;
import static com.example.DOTSAPI.filter.CustomAuthorizationFilter.decodeJWT;
import static com.example.DOTSAPI.configuration.SecurityConstants.SECRET;
import static com.example.DOTSAPI.configuration.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final AppUserServices appUserServices;

    @GetMapping("/users/list")
    public ResponseEntity<List<AppUser>> getAppUsers() {
        return ResponseEntity.ok().body(appUserServices.getAppUsers());
    }
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<AppUser> deleteAppUserById(@PathVariable Long id) {
        appUserServices.deleteAppUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/users/save")
    public ResponseEntity<AppUser> saveAppUser(@RequestBody @Valid AppUser appUser) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());

        return ResponseEntity.created(uri).body(appUserServices.saveAppUser(appUser));
    }
    @PostMapping("/roles/save")
    public ResponseEntity<Role> saveAppUser(@RequestBody @Valid Role role) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles/save").toUriString());
        return ResponseEntity.created(uri).body(appUserServices.saveRole(role));
    }

    @PostMapping("/roles/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody @Valid RoleToUserForm form) {
        appUserServices.addRoleToAppUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/refreshtoken")
    public void renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tokenHeader = request.getHeader(AUTHORIZATION);
        if(tokenHeader == null || !tokenHeader.startsWith(TOKEN_PREFIX)) {
            throw new RuntimeException("Refresh token is missing");
        }
        try {
            String refresh_token = tokenHeader.replace(TOKEN_PREFIX, "");
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
            DecodedJWT decodedJWT = decodeJWT(refresh_token,algorithm);
            String username = decodedJWT.getSubject();
            AppUser appUser = appUserServices.getUserByUserName(username);
            List<String> roles = appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList());
            String access_token = generateToken(roles, request.getRequestURL().toString(),
                    username, algorithm);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (RuntimeException e) {
            List<String> details = List.of(e.getMessage());
            ExceptionUtils.raiseException(request,response, "Validation failed", details, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            List<String> details = List.of("Token is invalid or expired");
            ExceptionUtils.raiseException(request,response, "Authentication failed", details, HttpStatus.UNAUTHORIZED);
        }
    }


}

@Data
class RoleToUserForm {
    @NotBlank(message = "username is missing")
    private String userName;
    @NotBlank(message = "password is missing")
    private String roleName;
}