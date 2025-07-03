package com.app.ecom_application.service;

import java.util.List;

import com.app.ecom_application.User;

public interface UserService {
    List<User> getUsers();
    User addUser(User user);
}
