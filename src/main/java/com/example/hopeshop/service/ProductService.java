package com.example.hopeshop.service;

import com.example.hopeshop.model.Brand;
import com.example.hopeshop.model.Category;
import com.example.hopeshop.model.Product;
import com.example.hopeshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> findAllByNameIsContaining(String name) {
        return productRepository.findAllByNameIsContaining(name);
    }

    public List<Product> findAllByCategory(Category category) {
        return productRepository.findAllByCategory(category);
    }

    public List<Product> findAllByBrandAndCategory(Brand brand, Category category) {
        return productRepository.findAllByBrandAndCategory(brand, category);
    }
}
