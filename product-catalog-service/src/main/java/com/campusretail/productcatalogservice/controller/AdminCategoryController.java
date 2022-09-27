package com.campusretail.productcatalogservice.controller;

import com.campusretail.productcatalogservice.entity.Category;
import com.campusretail.productcatalogservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {
	private final CategoryService service;
	
	@Autowired
	public AdminCategoryController(CategoryService service) {
		this.service = service;
	}
	
	@PostMapping("/categories")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		if (category != null)
			try {
				this.service.addCategory(category);
				return new ResponseEntity<>(category, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws Exception{
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
}
