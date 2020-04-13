package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.Category;
import com.example.hopeshop.model.User;
import com.example.hopeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //API trả về List Category.
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> listAllCategories() {
        List<Category> accounts = categoryService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Category>>(accounts, HttpStatus.OK);
    }

    //API trả về Category có ID trên url.
    @RequestMapping(value = "/category/{categoryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategoryById(@PathVariable("categoryName") String categoryName) {
        Category category = categoryService.findByName(categoryName);
        System.out.println("Fetching Category with id " + category.getId());
        Category account = categoryService.findById(category.getId());
        if (account == null) {
            System.out.println("Category with id " + category.getId() + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(account, HttpStatus.OK);
    }
}
