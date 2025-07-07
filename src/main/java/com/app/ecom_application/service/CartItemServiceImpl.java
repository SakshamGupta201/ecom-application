package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom_application.dto.CartItemRequestDTO;
import com.app.ecom_application.dto.CartItemResponsetDTO;
import com.app.ecom_application.models.CartItem;
import com.app.ecom_application.models.Product;
import com.app.ecom_application.models.User;
import com.app.ecom_application.repository.CartItemRepository;
import com.app.ecom_application.repository.ProductRepository;
import com.app.ecom_application.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CartItemResponsetDTO> getCartItems(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        List<CartItem> cartItems = userOpt.map(cartItemRepository::findByUser).orElse(null);
        if (cartItems == null || cartItems.isEmpty()) {
            return null;
        }

        List<CartItemResponsetDTO> cartItemResponseList = cartItems.stream().map(cartItem -> {
            CartItemResponsetDTO cartItemResponse = new CartItemResponsetDTO();
            cartItemResponse.setUserId(userId);
            cartItemResponse.setProductId(cartItem.getProduct().getId());
            cartItemResponse.setQuantity(cartItem.getQuantity());
            cartItemResponse.setPrice(cartItem.getPrice());
            cartItemResponse.setCreatedAt(cartItem.getCreatedAt());
            cartItemResponse.setUpdatedAt(cartItem.getUpdatedAt());
            return cartItemResponse;
        }).toList();

        return cartItemResponseList;

    }

    @Override
    @Transactional
    public boolean addCartItem(CartItemRequestDTO cartItemRequest, Integer userId) {
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        Optional<User> userOpt = userRepository.findById(userId);
        if (productOpt.isEmpty() || userOpt.isEmpty())
            return false;
        Product product = productOpt.get();
        if (product.getStockQuantity() < cartItemRequest.getQuantity() || cartItemRequest.getQuantity() <= 0)
            return false;
        User user = userOpt.get();
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
        int quantity = cartItemRequest.getQuantity();
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setPrice(product.getPrice() * quantity);
            cartItemRepository.save(newCartItem);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean removeCartItem(Integer productId, Integer userId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (productOpt.isEmpty() || userOpt.isEmpty())
            return false;
        cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
        return true;

    }
}
