package com.app.ecom_application.dto;

import com.app.ecom_application.enums.UserRole;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
