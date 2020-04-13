package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.Category;
import com.example.hopeshop.model.Product;
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

import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    //API trả về List Product.
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            Category category = new Category("SmartPhone");
            categoryService.save(category);
            Product product = new Product(1, "iPhone XS", 15000000, "", "256GB", category);
            productService.save(product);
            products = productService.findAll();
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/productByName/{productName}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByName(@PathVariable("productName") String name) {
        List<Product> products = productService.findAllByNameIsContaining(name);
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/productByCategory/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByCategory(@PathVariable("categoryId") int categoryId) {
        Category category = categoryService.findById(categoryId);
        List<Product> products = productService.findAllByCategory(category);
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
}
