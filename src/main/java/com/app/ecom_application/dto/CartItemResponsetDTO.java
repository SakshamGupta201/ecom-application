package com.app.ecom_application.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CartItemResponsetDTO {

    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

