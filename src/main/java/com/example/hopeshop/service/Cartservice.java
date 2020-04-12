package com.example.hopeshop.service;

import com.example.hopeshop.model.Cart;
import com.example.hopeshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Cartservice {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findById(int id) {
        return cartRepository.findById(id).get();
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public void deleteById(int id) {
        cartRepository.deleteById(id);
    }
}
