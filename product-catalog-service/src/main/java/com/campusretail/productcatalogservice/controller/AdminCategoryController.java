package com.campusretail.productcatalogservice.controller;

import com.campusretail.productcatalogservice.entity.Category;
import com.campusretail.productcatalogservice.exception.CategoryNotFoundException;
import com.campusretail.productcatalogservice.exception.RequestEmptyException;
import com.campusretail.productcatalogservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to manage all the 
 * operations for the admin entity
 */

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {
	private final CategoryService service;

	@Autowired
	public AdminCategoryController(CategoryService service) {
		this.service = service;
	}

	/**
	 * GET endpoint for returning a list of categories
	 * @return List\<Category\> representing all categories in database
	 * @throws Exception if the service did not complete the request successfully
	 */
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories(@RequestHeader("X-auth-role") String role) throws Exception {
		if (!role.equals("Admin"))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		List<Category> categories = this.service.getCategories().get();
		if (categories != null) {
			return new ResponseEntity<>(categories, HttpStatus.OK);
		}
		throw new CategoryNotFoundException("There are no categories");
	}


	/**
	 * Endpoint wich adds categorys for the products
	 * to te database
	 * @param category is the requested name of te category
	 * @return an HTTP response according to the obtained scenario
	 */
	@PostMapping("/categories")
	public ResponseEntity<Category> addCategory(@RequestBody Category category, @RequestHeader("X-auth-role") String role) {
		if (!role.equals("Admin"))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		if (category != null)
			try {
				this.service.saveCategory(category);
				return new ResponseEntity<>(category, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		throw new RequestEmptyException("Request body for category should not be empty");
	}


	/**
	 * Endpoint to delete a specific category searching by 
	 * the id of the category
	 * @param id is the actual id of the wanted to delete category
	 * @return an HTTP response according to the obtained scenario
	 * @throws Exception in case of bad working of asynchronous methods
	 * an exception
	 */
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id, @RequestHeader("X-auth-role") String role) throws Exception {
		if (!role.equals("Admin"))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		Category category = this.service.getCategoryById(id).get();
		if (category != null) {
			try {
				this.service.deleteCategory(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		throw new CategoryNotFoundException("Category with id " + id + " was not found");
	}

	/**
	 * Endpoint to update or change one category,
	 * searching it by the id of the wanted category
	 * @param id is the actual id of the wanted to update category
	 * @return an HTTP response according to the obtained scenario
	 * @throws Exception in case of bad working of asynchronous methods
	 */
	@PutMapping("/categories/{id}")
	public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestHeader("X-auth-role") String role) throws Exception {
		if (!role.equals("Admin"))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		Category category = this.service.getCategoryById(id).get();
		if (category != null)
			try {
				this.service.saveCategory(category);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		throw new CategoryNotFoundException("Category with id " + id + " was not found");
	}
}
