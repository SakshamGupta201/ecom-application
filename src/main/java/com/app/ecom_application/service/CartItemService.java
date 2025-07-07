package com.app.ecom_application.service;

import java.util.List;

import com.app.ecom_application.dto.CartItemRequestDTO;
import com.app.ecom_application.dto.CartItemResponsetDTO;

public interface CartItemService {
    /**
     * Adds a cart item for the user.
     *
     * @param cartItemRequest the request containing cart item details
     * @param userId          the ID of the user
     * @return true if the cart item was added successfully, false otherwise
     */
    public boolean addCartItem(CartItemRequestDTO cartItemRequest, Integer userId);

    /**
     * Removes a cart item for the user.
     *
     * @param productId the ID of the product to be removed
     * @param userId    the ID of the user
     * @return true if the cart item was removed successfully, false otherwise
     */
    public boolean removeCartItem(Integer productId, Integer userId);

    /**
     * Retrieves all cart items for the user.
     *
     * @param userId the ID of the user
     * @return a DTO containing the list of cart items
     */
    public List<CartItemResponsetDTO> getCartItems(Integer userId);
}