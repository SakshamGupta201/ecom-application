package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import com.app.ecom_application.User;

public interface UserService {
    List<User> getUsers();
    User addUser(User user);
    Optional<User> getUserById(int id);
    Optional<User> updateUser(int id, User user);
}
