package com.campusretail.productcatalogservice.controller;

import com.campusretail.productcatalogservice.entity.Product;
import com.campusretail.productcatalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService service;
	
	
	@Autowired
	public ProductController(ProductService service) {
		this.service = service;
	}

    @GetMapping (value = "/products")
    public ResponseEntity<List<Product>> getAllProducts() throws Exception{
        List<Product> products = this.service.getAllProduct().get();
        if(!products.isEmpty()) {
        	return new ResponseEntity<>(
        			products,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);       
    }

    @GetMapping(value = "/products", params = "category")
    public ResponseEntity<List<Product>> getAllProductByCategory(@RequestParam String category) throws Exception{
        List<Product> products = service.getAllProductByCategory(category).get();
        if(!products.isEmpty()) {
        	return new ResponseEntity<>(
        			products,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);
    }

    @GetMapping (value = "/products/{id}")
    public ResponseEntity<Product> getOneProductById(@PathVariable ("id") long id) throws Exception{
        Product product =  service.getProductById(id).get();
        if(product != null) {
        	return new ResponseEntity<>(
        			product,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);
    }

    @GetMapping (value = "/products", params = "name")
    public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam ("name") String name) throws Exception{
        List<Product> products =  service.getAllProductsByName(name).get();
        if(!products.isEmpty()) {
        	return new ResponseEntity<>(
        			products,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);
    }
}
