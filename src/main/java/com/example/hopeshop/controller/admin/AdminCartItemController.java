package com.example.hopeshop.controller.admin;

import com.example.hopeshop.model.CartItem;
import com.example.hopeshop.model.User;
import com.example.hopeshop.service.CartItemService;
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
public class AdminCartItemController {
    @Autowired
    private CartItemService cartItemService;

    //API trả về List CartItem.
    @RequestMapping(value = "/cartItem", method = RequestMethod.GET)
    public ResponseEntity<List<CartItem>> listAllCartItems(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CartItem> accounts = cartItemService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<CartItem>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<CartItem>>(accounts, HttpStatus.OK);
    }

    //API trả về CartItem có ID trên url.
    @RequestMapping(value = "/cartItem/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItem> getCartItemById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching CartItem with id " + id);
        CartItem account = cartItemService.findById(id);
        if (account == null) {
            System.out.println("CartItem with id " + id + " not found");
            return new ResponseEntity<CartItem>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CartItem>(account, HttpStatus.OK);
    }

    //API tạo một CartItem mới.
    @RequestMapping(value = "/cartItem", method = RequestMethod.POST)
    public ResponseEntity<Void> createCartItem(@RequestBody CartItem cartItem, UriComponentsBuilder ucBuilder, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Creating CartItem " + cartItem.getId());
        cartItemService.save(cartItem);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/cartItem/{id}").buildAndExpand(cartItem.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một CartItem với ID trên url.
    @RequestMapping(value = "/cartItem/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<CartItem> updateAdmin(@PathVariable("id") int id, @RequestBody CartItem cartItem, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Updating CartItem " + id);

        CartItem curremCartItem = cartItemService.findById(id);

        if (curremCartItem == null) {
            System.out.println("CartItem with id " + id + " not found");
            return new ResponseEntity<CartItem>(HttpStatus.NOT_FOUND);
        }

        cartItemService.save(cartItem);
        return new ResponseEntity<CartItem>(curremCartItem, HttpStatus.OK);
    }

    //API xóa một CartItem với ID trên url.
    @RequestMapping(value = "/cartItem/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CartItem> deleteCartItem(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");
        if (user.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching & Deleting CartItem with id " + id);

        CartItem cartItem = cartItemService.findById(id);
        if (cartItem == null) {
            System.out.println("Unable to delete. CartItem with id " + id + " not found");
            return new ResponseEntity<CartItem>(HttpStatus.NOT_FOUND);
        }

        cartItemService.deleteById(id);
        return new ResponseEntity<CartItem>(HttpStatus.NO_CONTENT);
    }
}
