package com.example.hopeshop.repository;

import com.example.hopeshop.model.Cart;
import com.example.hopeshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByCart(Cart cart);
}
