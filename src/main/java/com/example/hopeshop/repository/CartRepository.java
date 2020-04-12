package com.example.hopeshop.repository;

import com.example.hopeshop.model.Cart;
import com.example.hopeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByBuyer(User buyer);
    List<Cart> findAllByBuyDate(Date buyDate);
}
