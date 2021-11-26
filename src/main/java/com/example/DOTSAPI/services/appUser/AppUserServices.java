package com.example.DOTSAPI.services.appUser;

import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.model.Role;

import java.util.List;

public interface AppUserServices {
    AppUser saveAppUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToAppUser(String userName, String roleName);
    AppUser findUserByUserName(String userName);
    void deleteAppUserById(Long appUserId);
    List<AppUser> getAppUsers();
}
