package com.app.ecom_application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom_application.dto.OrderItemDTO;
import com.app.ecom_application.dto.OrderResponse;
import com.app.ecom_application.enums.OrderStatus;
import com.app.ecom_application.models.CartItem;
import com.app.ecom_application.models.Order;
import com.app.ecom_application.models.OrderItem;
import com.app.ecom_application.models.User;
import com.app.ecom_application.repository.OrderRepository;
import com.app.ecom_application.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(Integer userId) {
        // Validate for Cart Items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }
        // Validate for User
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User existingUser = userOpt.get();

        // Calculate Total Amount
        BigDecimal totalAmount = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(existingUser);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setOrderItems(cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                }).toList());

        // Save Order
        Order savedOrder = orderRepository.save(order);

        // Clear the Cart
        cartService.clearCart(userId);

        // Create OrderResponse
        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDTO(
                    orderItem.getProduct().getId(),
                    orderItem.getProduct().getId(),
                    orderItem.getQuantity(),
                    orderItem.getPrice()
                )).toList(),
            order.getCreatedAt(),
            order.getUpdatedAt()
        );
    }

}
