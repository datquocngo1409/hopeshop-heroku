package com.example.hopeshop.controller.admin;

import com.example.hopeshop.model.Category;
import com.example.hopeshop.model.User;
import com.example.hopeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    //API trả về List Category.
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> listAllCategories(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Category> accounts = categoryService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Category>>(accounts, HttpStatus.OK);
    }

    //API trả về Category có ID trên url.
    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching Category with id " + id);
        Category account = categoryService.findById(id);
        if (account == null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(account, HttpStatus.OK);
    }

    //API tạo một Category mới.
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ResponseEntity<Void> createCategory(@RequestBody Category category, UriComponentsBuilder ucBuilder, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Creating Category " + category.getName());
        categoryService.save(category);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Category với ID trên url.
    @RequestMapping(value = "/category/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Category> updateAdmin(@PathVariable("id") int id, @RequestBody Category category, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Updating Category " + id);

        Category curremCategory = categoryService.findById(id);

        if (curremCategory == null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }

        categoryService.save(category);
        return new ResponseEntity<Category>(curremCategory, HttpStatus.OK);
    }

    //API xóa một Category với ID trên url.
    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching & Deleting Category with id " + id);

        Category category = categoryService.findById(id);
        if (category == null) {
            System.out.println("Unable to delete. Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }

        categoryService.deleteById(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
    }
}
