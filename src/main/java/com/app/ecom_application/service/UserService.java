package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse addUser(UserRequest user);
    Optional<UserResponse> getUserById(int id);
    Optional<UserResponse> updateUser(int id, UserRequest user);
}
