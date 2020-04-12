package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.User;
import com.example.hopeshop.service.UserService;
import com.example.hopeshop.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody Map<String, String> map, HttpServletRequest req, HttpServletResponse resp) {
        String username = map.get("username");
        String password = map.get("password");
        boolean isRememberMe = false;
        if ("on".equalsIgnoreCase(map.get("remember"))) {
            isRememberMe = true;
        }
        System.out.println(username + " logined!");
        User user = service.login(username, password);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        HttpSession session = req.getSession();
        session.setAttribute("account", user);
        if(isRememberMe){
            saveRemeberMe(resp, username);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String email = map.get("email");
        String avatar = map.get("avatar");
        int roleId = 2;

        User user = service.login(username, password);
        if (user != null) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        user = new User(username, password, email, avatar, roleId);
        service.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    private void saveRemeberMe(HttpServletResponse response, String username){
        Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
        cookie.setMaxAge(30*60);
        response.addCookie(cookie);
    }
}
