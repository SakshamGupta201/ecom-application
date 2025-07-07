package com.app.ecom_application.dto;

import lombok.Data;

@Data
public class CartItemRequestDTO {

    private Integer productId;
    private Integer quantity;
    // private Integer userId; // Assuming this is needed to associate the cart item with a user
}
