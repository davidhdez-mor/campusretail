package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.domain.Product;
import com.campusretail.orderservice.feignclient.ProductClient;
import com.campusretail.orderservice.repository.CartRepository;
import com.campusretail.orderservice.utilities.CartUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//TODO: check if the methods works as intended

/**
 * Implementation of all the 
 * methods from the interface
 * of cart to do all the actions
 * needed to make work the 
 * controller
 */
@Service
public class CartServiceImpl implements CartService {

    private final ProductClient productClient;

    private final CartRepository cartRepository;
    
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

    @Override
    public void addItemToCart(String cartId, Long productId, Integer quantity) {
        Product product = productClient.getProductById(productId);
        Item item = new Item(quantity,product, CartUtilities.getSubTotalForItem(product,quantity));
        cartRepository.addItemToCart(cartId, item);
    }

    @Override
    public CompletableFuture<List<Item>> getCart(String cartId) {
        return CompletableFuture.completedFuture(cartRepository.getCart(cartId, Item.class));
    }

    @Override
    public void changeItemQuantity(String cartId, Long productId, Integer quantity) {
        List<Item> cart = cartRepository.getCart(cartId, Item.class);
        for(Item item : cart){
            if((item.getProduct().getId()).equals(productId)){
                cartRepository.deleteItemFromCart(cartId, item);
                item.setQuantity(quantity);
                item.setSubTotal(CartUtilities.getSubTotalForItem(item.getProduct(),quantity));
                cartRepository.addItemToCart(cartId, item);
            }
        }
    }

    @Override
    public void deleteItemFromCart(String cartId, Long productId) {
        List<Item> cart = cartRepository.getCart(cartId, Item.class);
        for(Item item : cart){
            if((item.getProduct().getId()).equals(productId)){
                cartRepository.deleteItemFromCart(cartId, item);
            }
        }
    }

    @Override
    public boolean checkIfItemExists(String cartId, Long productId) {
        List<Item> cart = cartRepository.getCart(cartId, Item.class);
        for(Item item : cart){
            if((item.getProduct().getId()).equals(productId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public CompletableFuture<List<Item>> getAllItemsFromCart(String cartId) {
        return CompletableFuture.completedFuture(cartRepository.getCart(cartId, Item.class));
    }

    @Override
    public void deleteCart(String cartId) {
        cartRepository.deleteCart(cartId);
    }
}
