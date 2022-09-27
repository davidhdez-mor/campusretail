package com.campusretail.productcatalogservice.controller;

import com.campusretail.productcatalogservice.dto.ReadCategoryDto;
import com.campusretail.productcatalogservice.dto.WriteProductDto;
import com.campusretail.productcatalogservice.entity.Category;
import com.campusretail.productcatalogservice.entity.Product;
import com.campusretail.productcatalogservice.service.ProductService;
import com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class AdminProductController {

	private final ProductService service;
	private final ModelMapper mapper;

	@Autowired
	public AdminProductController(ProductService service, ModelMapper mapper) {
		this.service = service;
		this.mapper = mapper;
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
	
	@PostMapping(value = "/products")
	private ResponseEntity<Product> addProduct(@RequestBody WriteProductDto writeProductDto) {
		if (writeProductDto != null) {
			try {
				Product product = this.mapper.map(writeProductDto, Product.class);
				List<Category> categories = writeProductDto.getCategory()
						.stream()
						.map(readCategoryDto -> this.mapper.map(readCategoryDto, Category.class))
						.collect(Collectors.toList());
				product.setCategory(categories);
				service.addProduct(product);
				return new ResponseEntity<>(product, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = "/products/{id}")
	private ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) throws Exception {
		Product product = service.getProductById(id).get();
		if (product != null) {
			try {
				service.deleteProduct(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
