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

    private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

    @GetMapping (value = "/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products =  productService.getAllProduct();
        if(!products.isEmpty()) {
        	return new ResponseEntity<>(
        			products,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);       
    }

    @GetMapping(value = "/products", params = "category")
    public ResponseEntity<List<Product>> getAllProductByCategory(@RequestParam ("category") String category){
        List<Product> products = productService.getAllProductByCategory(category);
        if(!products.isEmpty()) {
        	return new ResponseEntity<>(
        			products,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);
    }

    @GetMapping (value = "/products/{id}")
    public ResponseEntity<Product> getOneProductById(@PathVariable ("id") long id){
        Product product =  productService.getProductById(id);
        if(product != null) {
        	return new ResponseEntity<>(
        			product,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);
    }

    @GetMapping (value = "/products", params = "name")
    public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam ("name") String name){
        List<Product> products =  productService.getAllProductsByName(name);
        if(!products.isEmpty()) {
        	return new ResponseEntity<>(
        			products,
        			HttpStatus.OK);
        }
        return new ResponseEntity<>(
        		HttpStatus.NOT_FOUND);
    }
}
