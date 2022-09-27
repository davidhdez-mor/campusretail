package com.campusretail.productcatalogservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.campusretail.productcatalogservice.entity.Product;

public interface ProductService {
    CompletableFuture<List<Product>> getAllProduct();
    CompletableFuture<List<Product>> getAllProductByCategory(String category);
    CompletableFuture<Product> getProductById(Long id);
    CompletableFuture<List<Product>> getAllProductsByName(String name);
    CompletableFuture<Product> addProduct(Product product);
    void deleteProduct(Long productId);
}
