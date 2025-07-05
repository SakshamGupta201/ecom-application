package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom_application.models.User;
import com.app.ecom_application.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<User> updateUser(int id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPhone(user.getPhone());
                    existingUser.setRole(user.getRole());
                    existingUser.setAddress(user.getAddress());
                    return userRepository.save(existingUser);
                });
    }
}
