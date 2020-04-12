package com.example.hopeshop.controller.admin;

import com.example.hopeshop.model.User;
import com.example.hopeshop.service.UserService;
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
public class AdminUserController {
    @Autowired
    private UserService userService;

    //API trả về List User.
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<User> accounts = userService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(accounts, HttpStatus.OK);
    }

    //API trả về User có ID trên url.
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching User with id " + id);
        User account = userService.findById(id);
        if (account == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(account, HttpStatus.OK);
    }

    //API tạo một User mới.
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Creating User " + user.getUsername());
        userService.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một User với ID trên url.
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<User> updateAdmin(@PathVariable("id") int id, @RequestBody User user, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Updating User " + id);

        User curremUser = userService.findById(id);

        if (curremUser == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.save(user);
        return new ResponseEntity<User>(curremUser, HttpStatus.OK);
    }

    //API xóa một User với ID trên url.
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching & Deleting User with id " + id);

        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
