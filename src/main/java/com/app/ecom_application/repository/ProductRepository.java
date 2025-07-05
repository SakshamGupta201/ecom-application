package com.app.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ecom_application.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
