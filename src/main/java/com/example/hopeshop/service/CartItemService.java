package com.example.hopeshop.service;

import com.example.hopeshop.model.CartItem;
import com.example.hopeshop.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public CartItem findById(int id) {
        return cartItemRepository.findById(id).get();
    }

    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public void deleteById(int id) {
        cartItemRepository.deleteById(id);
    }
}
