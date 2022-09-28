package com.campusretail.productcatalogservice.controller;

import com.campusretail.productcatalogservice.entity.Product;
import com.campusretail.productcatalogservice.exception.ProductNotFoundException;
import com.campusretail.productcatalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller which manage all the standard
 * operations for the database
 * (Only read operations)
 */
@RestController
public class ProductController {

	private final ProductService service;


	@Autowired
	public ProductController(ProductService service) {
		this.service = service;
	}

	/**
	 * Endpoint to get all the products
	 * in the database
	 *
	 * @return a list of products inside the database
	 * @throws Exception in case of bad working of the
	 *                   asynchronous methods
	 */
	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> getAllProducts() throws Exception {
		List<Product> products = this.service.getAllProduct().get();
		if (!products.isEmpty()) {
			return new ResponseEntity<>(
					products,
					HttpStatus.OK);
		}
		throw new ProductNotFoundException("Products does not exists");
	}


	/**
	 * Endpoint to get specifics product using
	 * the category of the product to search it
	 *
	 * @return a list of products with the wanted
	 * category from the database
	 * @throws Exception in case of bad working of the
	 *                   asynchronous methods
	 */
	@GetMapping(value = "/products", params = "category")
	public ResponseEntity<List<Product>> getAllProductByCategory(@RequestParam String category) throws Exception {
		List<Product> products = service.getAllProductByCategory(category).get();
		if (!products.isEmpty()) {
			return new ResponseEntity<>(
					products,
					HttpStatus.OK);
		}
		throw new ProductNotFoundException("There are no products with " + category + " category");
	}

	/**
	 * Endpoint which search a specific product
	 * using the id to search it
	 *
	 * @param id is the id of the wanted product
	 * @return a single entity of product
	 * @throws Exception in case of asynchronous
	 *                   methods fails
	 */
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> getOneProductById(@PathVariable Long id) throws Exception {
		Product product = service.getProductById(id).get();
		if (product != null) {
			return new ResponseEntity<>(
					product,
					HttpStatus.OK);
		}
		throw new ProductNotFoundException("Product with id " + id + "was not found");
	}

	/**
	 * Endpoint which search a specific product
	 * using the name to search it
	 *
	 * @param name is the actual name of the
	 *             wanted product
	 * @return a list of products which have the
	 * requested name
	 * @throws Exception in case of asynchronous
	 *                   methods fails
	 */
	@GetMapping(value = "/products", params = "name")
	public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam("name") String name) throws Exception {
		List<Product> products = service.getAllProductsByName(name).get();
		if (!products.isEmpty()) {
			return new ResponseEntity<>(
					products,
					HttpStatus.OK);
		}
		throw new ProductNotFoundException("Products with name" + name + "were not found");
	}
}
