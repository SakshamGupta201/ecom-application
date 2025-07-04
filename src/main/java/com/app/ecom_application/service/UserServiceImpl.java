package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom_application.User;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users = new java.util.ArrayList<>();

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User addUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<User> updateUser(int id, User user) {
        return getUserById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    return existingUser;
                });
    }
}
