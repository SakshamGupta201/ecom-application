package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom_application.dto.AddressDTO;
import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;
import com.app.ecom_application.models.Address;
import com.app.ecom_application.models.User;
import com.app.ecom_application.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse addUser(UserRequest user) {
        User newUser = mapToUser(user);
        User savedUser = userRepository.save(newUser);
        return mapToUserResponse(savedUser);
    }

    @Override
    public Optional<UserResponse> getUserById(int id) {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    @Override
    @Transactional
    public Optional<UserResponse> updateUser(int id, UserRequest user) {
        return userRepository.findById(id).map(existingUser -> {
            updateUserFromRequest(existingUser, user);
            User updatedUser = userRepository.save(existingUser);
            return mapToUserResponse(updatedUser);
        });
    }

    /**
     * Maps a User entity to UserResponse DTO
     * 
     * @param user the user entity to map
     * @return the mapped UserResponse DTO
     */
    private UserResponse mapToUserResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setAddress(user.getAddress() != null ? mapToAddressDTO(user.getAddress()) : null);

        return response;
    }

    /**
     * Maps an Address entity to AddressDTO
     * 
     * @param address the address entity to map
     * @return the mapped AddressDTO
     */
    private AddressDTO mapToAddressDTO(Address address) {
        if (address == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setCountry(address.getCountry());

        return addressDTO;
    }

    /**
     * Maps a UserRequest DTO to User entity
     * 
     * @param userRequest the user request DTO to map
     * @return the mapped User entity
     */
    private User mapToUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZipCode(userRequest.getAddress().getZipCode());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }

        return user;
    }

    /**
     * Updates an existing User entity with values from UserRequest
     * 
     * @param existingUser the user entity to update
     * @param userRequest  the user request DTO with new values
     */
    private void updateUserFromRequest(User existingUser, UserRequest userRequest) {
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = existingUser.getAddress();
            if (address == null) {
                address = new Address();
                existingUser.setAddress(address);
            }
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZipCode(userRequest.getAddress().getZipCode());
            address.setCountry(userRequest.getAddress().getCountry());
        }
    }
}
