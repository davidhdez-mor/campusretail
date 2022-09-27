package com.campusretail.productcatalogservice.controller;

import com.campusretail.productcatalogservice.entity.Category;
import com.campusretail.productcatalogservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	 * Endpoint wich adds categorys for the products
	 * to te database
	 * @param category is the requested name of te category
	 * @return an HTTP response according to the obtained scenario
	 */
	@PostMapping("/categories")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		if (category != null)
			try {
				this.service.saveCategory(category);
				return new ResponseEntity<>(category, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws Exception {
		Category category = this.service.getCategoryById(id).get();
		if (category != null) {
			try {
				this.service.deleteCategory(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Endpoint to update or change one category,
	 * searching it by the id of the wanted category
	 * @param id is the actual id of the wanted to update category
	 * @return an HTTP response according to the obtained scenario
	 * @throws Exception in case of bad working of asynchronous methods
	 */
	@PutMapping("/categories/{id}")
	public ResponseEntity<Void> updateCategory(@PathVariable Long id) throws Exception {
		Category category = this.service.getCategoryById(id).get();
		if (category != null)
			try {
				this.service.saveCategory(category);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
