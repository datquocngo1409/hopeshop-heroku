package com.example.hopeshop.service;

import com.example.hopeshop.model.Brand;
import com.example.hopeshop.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(int id) {
        return brandRepository.findById(id).get();
    }

    public void save(Brand b) {
        brandRepository.save(b);
    }

    public void deleteById(int id) {
        brandRepository.deleteById(id);
    }

    public List<Brand> findAllByCategoryId(int categoryId) {
        return brandRepository.findAllByCategoryId(categoryId);
    }

    public Brand findByName(String name) {
        return brandRepository.findByName(name);
    }
}
