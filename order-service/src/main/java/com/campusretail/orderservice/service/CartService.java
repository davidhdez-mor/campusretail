package com.campusretail.orderservice.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Item;

/**
 * Interface with all the needed
 * methods for the implementation
 * of Cart class
 */
public interface CartService {

    void addItemToCart(Long cartId, Long productId, Integer quantity);
    CompletableFuture<Optional<Cart>> getCart(Long cartId);
    void changeItemQuantity(Long cartId, Long productId, Integer quantity);
    void deleteItemFromCart(Long cartId, Long productId);
    boolean checkIfItemExists(Long cartId, Long productId);
    CompletableFuture<List<Item>> getAllItemsFromCart(Long cartId);
    void deleteCart(Long cartId);
}
