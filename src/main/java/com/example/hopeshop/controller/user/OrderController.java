package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.Cart;
import com.example.hopeshop.model.CartItem;
import com.example.hopeshop.model.User;
import com.example.hopeshop.service.CartItemService;
import com.example.hopeshop.service.Cartservice;
import com.example.hopeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private Cartservice cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    public JavaMailSender emailSender;

    long time = System.currentTimeMillis();

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<String> payOrder(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Object obj = session.getAttribute("account");
        User buyer = (User) obj;
        Cart cart = new Cart();
        cart.setBuyer(buyer);
        cart.setBuyDate(new java.sql.Date(time));
        cartService.save(cart);
        Object objCart = session.getAttribute("cart");

        if (objCart != null) {
            Map<Integer, CartItem> mapCartItem = (Map<Integer, CartItem>) objCart;
            String productBuy = "";
            for (CartItem cartItem : mapCartItem.values()) {
                productBuy = productBuy + cartItem.getProduct().getName() + ", ";
            }

            productBuy = productBuy.substring(0, productBuy.length() -2);

            for (CartItem cartItem : mapCartItem.values()) {
                cartItem.setCart(cart);
                cartItemService.save(cartItem);
            }
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(cart.getBuyer().getEmail());
                message.setSubject("Thank for your payment " + cart.getBuyer().getUsername());
                message.setText("Payment success. Your " + productBuy + " will send to you soon!");
                emailSender.send(message);
                System.out.println(cart.getBuyer() + ": Payment success.");
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
        session.removeAttribute("cart");
        return new ResponseEntity<String>("Payment success", HttpStatus.OK);
    }
}
