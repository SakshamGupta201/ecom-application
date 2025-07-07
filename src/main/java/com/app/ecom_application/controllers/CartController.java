package com.app.ecom_application.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom_application.dto.CartItemRequestDTO;
import com.app.ecom_application.dto.CartItemResponsetDTO;
import com.app.ecom_application.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<List<CartItemResponsetDTO>> getCartItems(
            @RequestHeader("X-User-Id") Integer userId) {
        List<CartItemResponsetDTO> cartItems = cartItemService.getCartItems(userId);

        return cartItems != null
                ? ResponseEntity.ok(cartItems)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") Integer userId,
            @RequestBody CartItemRequestDTO cartItemRequest) {
        boolean isAdded = cartItemService.addCartItem(cartItemRequest, userId);
        if (isAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.badRequest().body("Product not available or user not found");
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-Id") Integer userId,
            @PathVariable Integer productId) {
        boolean isRemoved = cartItemService.removeCartItem(productId, userId);
        if (isRemoved) {
            return ResponseEntity.ok("Item removed from cart");
        } else {
            return ResponseEntity.badRequest().body("Product not found in cart or user not found");
        }
    }
}
