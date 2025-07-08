package com.app.ecom_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ecom_application.models.CartItem;
import com.app.ecom_application.models.Product;
import com.app.ecom_application.models.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    /**
     * Finds a CartItem by the associated User and Product.
     * @param user the User associated with the CartItem
     * @param product the Product associated with the CartItem
     * @return the CartItem if found, otherwise null
     */
    CartItem findByUserAndProduct(User user, Product product);

    /**
     * Deletes a CartItem by the associated User and Product.
     * @param user the User associated with the CartItem
     * @param product the Product associated with the CartItem
     */
    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
