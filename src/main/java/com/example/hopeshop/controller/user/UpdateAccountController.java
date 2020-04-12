package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.User;
import com.example.hopeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UpdateAccountController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public ResponseEntity<User> updateAccount(HttpServletRequest req, HttpServletResponse resp, @RequestBody User user) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account == null) {
            System.out.println("You are not login!");
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        } else if (account.getId() != user.getId()) {
            System.out.println("Your account is invalid!");
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        } else {
            User oldUser = userService.findById(user.getId());
            user.setRoleId(oldUser.getRoleId());
            userService.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }
}
