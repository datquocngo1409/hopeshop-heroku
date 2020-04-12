package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.Cart;
import com.example.hopeshop.model.CartItem;
import com.example.hopeshop.model.Product;
import com.example.hopeshop.model.User;
import com.example.hopeshop.service.Cartservice;
import com.example.hopeshop.service.ProductService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private Cartservice cartservice;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<List<CartItem>> addCart(HttpServletRequest req, HttpServletResponse resp, @RequestBody Map<String, String> map) {
        String productId = map.get("productId");
        String productQuantity = map.get("productQuantity");
        Product product = productService.findById(Integer.parseInt(productId));
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(Integer.parseInt(productQuantity));
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setProduct(product);
        HttpSession httpSession = req.getSession();
        Object obj = httpSession.getAttribute("cart");
        Map<Integer, CartItem> cartItemMap = new HashMap<Integer, CartItem>();
        if (obj == null) {
            cartItemMap.put(cartItem.getProduct().getId(), cartItem);
            httpSession.setAttribute("cart", cartItemMap);
        } else {
            cartItemMap = (Map<Integer, CartItem>) obj;

            CartItem existedCartItem = cartItemMap.get(Integer.valueOf(productId));

            if (existedCartItem == null) {
                cartItemMap.put(product.getId(), cartItem);
            } else {
                existedCartItem.setQuantity(existedCartItem.getQuantity() + Integer.parseInt(productQuantity));
            }

            httpSession.setAttribute("cart", cartItemMap);
        }
        List<CartItem> cartItemList = new ArrayList<CartItem>(cartItemMap.values());
        return new ResponseEntity<List<CartItem>>(cartItemList, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<List<CartItem>> removeCart(HttpServletRequest req, HttpServletResponse resp, @RequestBody Map<String, String> map) {
        HttpSession httpSession = req.getSession();
        Object obj = httpSession.getAttribute("cart");
        String pId = map.get("productId");
        List<CartItem> cartItemList = new ArrayList<CartItem>();
        if (obj != null) {
            Map<Integer, CartItem> cartItemMap = (Map<Integer, CartItem>) obj;
            cartItemMap.remove(Integer.parseInt(pId));
            httpSession.setAttribute("cart", cartItemMap);
            cartItemList = new ArrayList<CartItem>(cartItemMap.values());
        }
        return new ResponseEntity<List<CartItem>>(cartItemList, HttpStatus.OK);
    }
}
