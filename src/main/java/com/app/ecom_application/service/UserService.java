package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import com.app.ecom_application.dto.UserRequestDTO;
import com.app.ecom_application.dto.UserResponseDTO;

public interface UserService {
    List<UserResponseDTO> getUsers();
    UserResponseDTO addUser(UserRequestDTO user);
    Optional<UserResponseDTO> getUserById(int id);
    Optional<UserResponseDTO> updateUser(int id, UserRequestDTO user);
}
