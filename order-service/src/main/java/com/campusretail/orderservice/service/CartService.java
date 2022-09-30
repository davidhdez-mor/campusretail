package com.campusretail.orderservice.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.campusretail.orderservice.domain.Cart;

/**
 * Interface with all the needed
 * methods for the implementation
 * of Cart class
 */
public interface CartService {

    CompletableFuture<Cart> addItemToCart(Long userId, Long productId, Integer quantity) throws InterruptedException, ExecutionException;
    CompletableFuture<Cart> getCart(Long userId);
    CompletableFuture<Cart> deleteItemFromCart(Long userId, Long productId);
    CompletableFuture<Cart> deleteAllItemsFromCart(Long userId);
}
