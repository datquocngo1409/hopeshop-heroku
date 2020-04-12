package com.example.hopeshop.controller.admin;

import com.example.hopeshop.model.Cart;
import com.example.hopeshop.model.User;
import com.example.hopeshop.service.Cartservice;
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
public class AdminCartController {
    @Autowired
    private Cartservice cartService;

    //API trả về List Cart.
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> listAllCarts(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<List<Cart>>(HttpStatus.BAD_REQUEST);
        }
        List<Cart> accounts = cartService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Cart>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Cart>>(accounts, HttpStatus.OK);
    }

    //API trả về Cart có ID trên url.
    @RequestMapping(value = "/cart/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> getCartById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Fetching Cart with id " + id);
        Cart account = cartService.findById(id);
        if (account == null) {
            System.out.println("Cart with id " + id + " not found");
            return new ResponseEntity<Cart>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cart>(account, HttpStatus.OK);
    }

    //API tạo một Cart mới.
    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public ResponseEntity<Void> Cart(@RequestBody Cart cart, UriComponentsBuilder ucBuilder, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Creating Cart " + cart.getId());
        cartService.save(cart);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/cart/{id}").buildAndExpand(cart.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Cart với ID trên url.
    @RequestMapping(value = "/cart/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Cart> updateAdmin(@PathVariable("id") int id, @RequestBody Cart cart, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Updating Cart " + id);

        Cart curremCart = cartService.findById(id);

        if (curremCart == null) {
            System.out.println("Cart with id " + id + " not found");
            return new ResponseEntity<Cart>(HttpStatus.NOT_FOUND);
        }

        cartService.save(cart);
        return new ResponseEntity<Cart>(curremCart, HttpStatus.OK);
    }

    //API xóa một Cart với ID trên url.
    @RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Cart> deleteCart(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching & Deleting Cart with id " + id);

        Cart cart = cartService.findById(id);
        if (cart == null) {
            System.out.println("Unable to delete. Cart with id " + id + " not found");
            return new ResponseEntity<Cart>(HttpStatus.NOT_FOUND);
        }

        cartService.deleteById(id);
        return new ResponseEntity<Cart>(HttpStatus.NO_CONTENT);
    }
}
