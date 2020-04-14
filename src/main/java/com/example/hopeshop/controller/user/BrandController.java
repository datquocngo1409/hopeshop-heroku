package com.example.hopeshop.controller.user;

import com.example.hopeshop.model.Brand;
import com.example.hopeshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;

    //API trả về List Brand có CategoryID trên url.
    @RequestMapping(value = "/brandByCategory/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Brand>> getBrandByCategoryId(@PathVariable("id") int id) {
        System.out.println("Fetching List Brand with CategoryId " + id);
        List<Brand> brandList = brandService.findAllByCategoryId(id);
        if (brandList == null) {
            System.out.println("List Brand with id " + id + " not found");
            return new ResponseEntity<List<Brand>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
    }

    //API trả về Category có ID trên url.
    @RequestMapping(value = "/brandByName/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Brand> getCategoryById(@PathVariable("name") String name) {
        System.out.println("Fetching Brand with name " + name);
        Brand brand = brandService.findByName(name);
        if (brand == null) {
            System.out.println("Category with id " + name + " not found");
            return new ResponseEntity<Brand>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Brand>(brand, HttpStatus.OK);
    }
}
