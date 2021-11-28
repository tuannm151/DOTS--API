package com.example.DOTSAPI.services.appUser;

import com.example.DOTSAPI.model.Customer;
import com.example.DOTSAPI.model.User;
import com.example.DOTSAPI.model.Role;

import java.util.List;

public interface AppUserServices {
    User saveAppUser(User user);
    Role saveRole(Role role);
    void addRoleToAppUser(String userName, String roleName);
    User findUserByUserName(String userName);
    void deleteAppUserById(Long appUserId);
    List<User> getAppUsers();
    void addCustomer(Customer customer, User user);
}
