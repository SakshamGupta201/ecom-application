package com.app.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ecom_application.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
