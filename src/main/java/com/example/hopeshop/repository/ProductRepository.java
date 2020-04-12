package com.example.hopeshop.repository;

import com.example.hopeshop.model.Category;
import com.example.hopeshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByNameIsContaining(String name);
    List<Product> findAllByCategory(Category category);
}
