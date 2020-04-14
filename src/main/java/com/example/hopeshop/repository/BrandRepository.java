package com.example.hopeshop.repository;

import com.example.hopeshop.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByCategoryId(int categoryId);
    Brand findByName(String name);
}
