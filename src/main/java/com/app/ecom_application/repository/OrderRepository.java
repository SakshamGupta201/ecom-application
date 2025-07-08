package com.app.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ecom_application.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
