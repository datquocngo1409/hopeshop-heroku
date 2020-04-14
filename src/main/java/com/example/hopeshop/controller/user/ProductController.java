package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.Brand;
import com.example.hopeshop.model.Category;
import com.example.hopeshop.model.Product;
import com.example.hopeshop.service.BrandService;
import com.example.hopeshop.service.CategoryService;
import com.example.hopeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    //API trả về List Product.
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/productByName/{productName}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByName(@PathVariable("productName") String name) {
        List<Product> products = productService.findAllByNameIsContaining(name);
        if (products.isEmpty()) {
            products = new ArrayList<>();
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/productByCategory/{categoryName}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByCategory(@PathVariable("categoryName") String categoryName) {
        Category category = categoryService.findByName(categoryName);
        List<Product> products = productService.findAllByCategory(category);
        if (products.isEmpty()) {
            products = new ArrayList<>();
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/productByBrandAndCategory/{categoryName}/{brandName}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByBrandAndCategory(@PathVariable("categoryName") String categoryName, @PathVariable("brandName") String brandName) {
        Category category = categoryService.findByName(categoryName);
        Brand brand = brandService.findByName(brandName);
        List<Product> products = productService.findAllByBrandAndCategory(brand, category);
        if (products.isEmpty()) {
            products = new ArrayList<>();
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
}
