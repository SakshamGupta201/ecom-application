package com.app.ecom_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.ecom_application.models.CartItem;
import com.app.ecom_application.repository.CartItemRepository;
import com.app.ecom_application.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public List<CartItem> getCart(Integer userId) {
        return userRepository.findById(userId)
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }

    public void clearCart(Integer userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    cartItemRepository.deleteByUser(user);
                });
    }

}
