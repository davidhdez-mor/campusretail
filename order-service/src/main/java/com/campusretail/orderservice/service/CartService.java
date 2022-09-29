package com.campusretail.orderservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.campusretail.orderservice.domain.Item;

/**
 * Interface with all the needed
 * methods for the implementation
 * of Cart class
 */
public interface CartService {

    void addItemToCart(String cartId, Long productId, Integer quantity);
    CompletableFuture<List<Item>> getCart(String cartId);
    void changeItemQuantity(String cartId, Long productId, Integer quantity);
    void deleteItemFromCart(String cartId, Long productId);
    boolean checkIfItemExists(String cartId, Long productId);
    CompletableFuture<List<Item>> getAllItemsFromCart(String cartId);
    void deleteCart(String cartId);
}
