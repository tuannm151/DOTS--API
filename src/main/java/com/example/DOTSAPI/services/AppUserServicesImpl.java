package com.example.DOTSAPI.services;

import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.model.Role;
import com.example.DOTSAPI.repository.RoleRepo;
import com.example.DOTSAPI.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServicesImpl implements AppUserServices, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final Argon2PasswordEncoder argon2PasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUserName(username);

        if (appUser == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(appUser.getUserName(), appUser.getPassword(),
                authorities);
    }
    @Override
    public AppUser saveAppUser(AppUser appUser) {
        log.info("Saving new user {} to the database", appUser.getUserName());
        appUser.setPassword(argon2PasswordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(List.of(roleRepo.getRoleByName("ROLE_USER")));
        return userRepo.save(appUser);
    }

    @Override
    public void deleteAppUserById(Long appUserId) {
        userRepo.deleteById(appUserId);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToAppUser(String userName, String roleName) {
        log.info("Adding a role {} to an exist user {}", roleName, userName);
        AppUser appUser = userRepo.findByUserName(userName);
        Role role = roleRepo.getRoleByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUserByUserName(String userName) {
        log.info("Fetching user {}", userName);
        return userRepo.findByUserName(userName);
    }

    @Override
    public List<AppUser> getAppUsers() {
        log.info("Fetching all user");
        return userRepo.findAll();
    }

}
