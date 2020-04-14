package com.example.hopeshop.controller.admin;

import com.example.hopeshop.model.Brand;
import com.example.hopeshop.model.User;
import com.example.hopeshop.service.BrandService;
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
public class AdminBrandController {
    @Autowired
    private BrandService brandService;

    //API trả về List Brand.
    @RequestMapping(value = "/brand", method = RequestMethod.GET)
    public ResponseEntity<List<Brand>> listAllBrands(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Brand> accounts = brandService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Brand>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Brand>>(accounts, HttpStatus.OK);
    }

    //API trả về Brand có ID trên url.
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Brand> getBrandById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching Brand with id " + id);
        Brand brand = brandService.findById(id);
        if (brand == null) {
            System.out.println("Brand with id " + id + " not found");
            return new ResponseEntity<Brand>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Brand>(brand, HttpStatus.OK);
    }

    //API tạo một Brand mới.
    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    public ResponseEntity<Void> createBrand(@RequestBody Brand brand, UriComponentsBuilder ucBuilder, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Creating Brand " + brand.getName());
        brandService.save(brand);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/brand/{id}").buildAndExpand(brand.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Brand với ID trên url.
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Brand> updateAdmin(@PathVariable("id") int id, @RequestBody Brand brand, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Updating Brand " + id);

        Brand curremBrand = brandService.findById(id);

        if (curremBrand == null) {
            System.out.println("Brand with id " + id + " not found");
            return new ResponseEntity<Brand>(HttpStatus.NOT_FOUND);
        }

        brandService.save(brand);
        return new ResponseEntity<Brand>(curremBrand, HttpStatus.OK);
    }

    //API xóa một Brand với ID trên url.
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Brand> deleteBrand(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");
        if (account.getRoleId() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Fetching & Deleting Brand with id " + id);

        Brand brand = brandService.findById(id);
        if (brand == null) {
            System.out.println("Unable to delete. Brand with id " + id + " not found");
            return new ResponseEntity<Brand>(HttpStatus.NOT_FOUND);
        }

        brandService.deleteById(id);
        return new ResponseEntity<Brand>(HttpStatus.NO_CONTENT);
    }
}
