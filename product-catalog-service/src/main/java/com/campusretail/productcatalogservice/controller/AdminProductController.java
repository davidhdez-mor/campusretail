package com.campusretail.productcatalogservice.controller;

import com.campusretail.productcatalogservice.entity.Product;
import com.campusretail.productcatalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminProductController {

	private final ProductService productService;

	@Autowired
	public AdminProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(value = "/products")
	private ResponseEntity<Product> addProduct(@RequestBody Product product) {
		if (product != null) {
			try {
				productService.addProduct(product);
				return new ResponseEntity<>(product, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = "/products/{id}")
	private ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			try {
				productService.deleteProduct(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
