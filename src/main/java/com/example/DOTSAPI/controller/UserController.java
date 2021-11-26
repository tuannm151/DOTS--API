package com.example.DOTSAPI.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.DOTSAPI.exception.CustomAuthenticationException;
import com.example.DOTSAPI.exception.ExceptionUtils;
import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.model.Role;
import com.example.DOTSAPI.services.appUser.AppUserServices;
import com.example.DOTSAPI.services.AuthServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.security.sasl.AuthenticationException;
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
import static com.example.DOTSAPI.configuration.SecurityConstants.SECRET;
import static com.example.DOTSAPI.configuration.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final AppUserServices appUserServices;
    private final AuthServices authServices;
    @PostMapping("/users/save")
    public ResponseEntity<AppUser> saveAppUser(@RequestBody @Valid AppUser appUser) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());

        return ResponseEntity.created(uri).body(appUserServices.saveAppUser(appUser));
    }


    @GetMapping("/users/refreshtoken")
    public void renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tokenHeader = request.getHeader(AUTHORIZATION);
        if(tokenHeader == null || !tokenHeader.startsWith(TOKEN_PREFIX)) {
            throw new CustomAuthenticationException("Refresh token is missing");
        }
        String refresh_token = tokenHeader.replace(TOKEN_PREFIX, "");
        AppUser appUser = authServices.loadUsernameByToken(refresh_token);
        List<String> roles = appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        String access_token = generateToken(roles, request.getRequestURL().toString(),
                appUser.getUserName(), Algorithm.HMAC256(SECRET.getBytes()));
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @PostMapping("/admin/roles/save")
    public ResponseEntity<Role> saveAppUser(@RequestBody @Valid Role role) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/admin/roles/save").toUriString());
        return ResponseEntity.created(uri).body(appUserServices.saveRole(role));
    }

    @PostMapping("/admin/roles/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody @Valid RoleToUserForm form) {
        appUserServices.addRoleToAppUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/admin/users/delete/{id}")
    public ResponseEntity<AppUser> deleteAppUserById(@PathVariable Long id) {
        appUserServices.deleteAppUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/admin/users/list")
    public ResponseEntity<List<AppUser>> getAppUsers() {
        return ResponseEntity.ok().body(appUserServices.getAppUsers());
    }


}

@Data
class RoleToUserForm {
    @NotBlank(message = "username is missing")
    private String userName;
    @NotBlank(message = "password is missing")
    private String roleName;
}