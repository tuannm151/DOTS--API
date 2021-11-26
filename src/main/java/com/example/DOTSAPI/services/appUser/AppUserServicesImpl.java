package com.example.DOTSAPI.services.appUser;

import com.example.DOTSAPI.exception.NotFoundException;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServicesImpl implements AppUserServices, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final Argon2PasswordEncoder argon2PasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUserName(username);

        if (appUser == null) {
            throw new UsernameNotFoundException("User not found");
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
        appUser.setPassword(argon2PasswordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(Set.of(roleRepo.findRoleByName("ROLE_USER")));
        return userRepo.save(appUser);
    }

    @Override
    public void deleteAppUserById(Long appUserId) {
        if(!userRepo.existsById(appUserId)) throw new NotFoundException("User not found");
        userRepo.deleteById(appUserId);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToAppUser(String userName, String roleName) {
        log.info("Adding a role {} to an exist user {}", roleName, userName);
        AppUser appUser = userRepo.findByUserName(userName);
        Role role = roleRepo.findRoleByName(roleName);
        if(role == null) {
            throw new NotFoundException("Role not found");
        }
        if(appUser == null) {
            throw new NotFoundException("User not found");
        }
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser findUserByUserName(String userName) {
        AppUser appUser = userRepo.findByUserName(userName);
        if(appUser == null) {
            throw new NotFoundException("User not found");
        }
        return appUser;
    }

    @Override
    public List<AppUser> getAppUsers() {
        return userRepo.findAll();
    }

}
