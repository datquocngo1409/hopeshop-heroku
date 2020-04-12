package com.example.hopeshop.controller.admin;

import com.example.hopeshop.model.User;
import com.example.hopeshop.service.UserService;
import com.example.hopeshop.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {
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
        System.out.println("Admin " + username + " logined!");
        User user = service.login(username, password);
        if (user == null || user.getRoleId() != 1) {
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

        String adminKey = map.get("adminKey");
        if (!adminKey.equalsIgnoreCase("admin")) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

        int id = Integer.parseInt(map.get("id"));
        if (service.findById(id) != null) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        String username = map.get("username");
        String password = map.get("password");
        String email = map.get("email");
        String avatar = map.get("avatar");
        int roleId = 1;
        User user = null;
        try {
            user = service.login(username, password);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user != null) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        user = new User(id, username, password, email, avatar, roleId);
        service.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    private void saveRemeberMe(HttpServletResponse response, String username){
        Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
        cookie.setMaxAge(30*60);
        response.addCookie(cookie);
    }
}
