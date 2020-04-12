package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.User;
import com.example.hopeshop.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LogoutController {
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<String> login(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("account");
        session.removeAttribute("account"); //remove session

        Cookie[] cookies = req.getCookies();

        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(Constant.COOKIE_REMEMBER.equals(cookie.getName())){
                    cookie.setMaxAge(0); // <=> remove cookie
                    resp.addCookie(cookie); // add again
                    break;
                }
            }
        }
        String result;
        if (user != null) {
            result = user.getUsername() + " logouted!";
        } else {
            result = "You have not logined yet!";
        }
        System.out.println(result);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
