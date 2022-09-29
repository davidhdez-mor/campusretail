package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.domain.Product;
import com.campusretail.orderservice.exception.CartNotFoundException;
import com.campusretail.orderservice.feignclient.ProductClient;
import com.campusretail.orderservice.repository.CartRepository;
import com.campusretail.orderservice.utilities.CartUtilities;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

//TODO: check if the methods works as intended

/**
 * Implementation of all the
 * methods from the interface
 * of cart to do all the actions
 * needed to make work the
 * controller
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {

	private final ProductClient productClient;

	private final CartRepository cartRepository;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, ProductClient productClient) {
		this.cartRepository = cartRepository;
		this.productClient = productClient;
	}

	@Override
	public void addItemToCart(Long cartId, Long productId, Integer quantity) {
		Product product = productClient.getProductById(productId);
		Item item = new Item(quantity, CartUtilities.getSubTotalForItem(product, quantity), product);
		Optional<Cart> optionalCart = cartRepository.findById(cartId);
		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();
			List<Item> items = cart.getItems();
			items.add(item);
			cart.setItems(items);
			CompletableFuture.runAsync(() -> cartRepository.save(cart));
		}
	}

	@Override
	public CompletableFuture<Optional<Cart>> getCart(Long cartId) {
		Optional<Cart> optionalCart = cartRepository.findById(cartId);
		if (optionalCart.isPresent())
			return CompletableFuture.completedFuture(optionalCart);
		throw new CartNotFoundException("");
	}

	@Override
	public void changeItemQuantity(Long cartId, Long productId, Integer quantity) {
		Cart cart = cartRepository.findById(cartId).orElse(null);
		List<Item> items = cart.getItems()
				.stream()
				.filter(item -> 
					productId.equals(item.getProduct().getId())
				)
				.collect(Collectors.toList());
		items.forEach(item -> item.setQuantity(quantity));
		cart.setItems(items);
		CompletableFuture.runAsync(() -> cartRepository.save(cart));
	}
	
	@Override
	public void deleteItemFromCart(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId).orElse(null);
		List<Item> items = cart.getItems();
		items.removeIf(item -> item.getProduct().getId().equals(productId));
		cart.setItems(items);
		CompletableFuture.runAsync(() -> cartRepository.save(cart));
	}

	@Override
	public CompletableFuture<Boolean> checkIfItemExists(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId).orElse(null);
		return CompletableFuture.completedFuture(cart.getItems()
				.stream()
				.anyMatch(item -> item.getProduct().getId().equals(productId)));
	}

	@Override
	public CompletableFuture<List<Item>> getAllItemsFromCart(Long cartId) {
		return CompletableFuture.completedFuture(cartRepository.findById(cartId).orElse(null).getItems());
	}

	@Override
	public void deleteCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElse(null);
		CompletableFuture.runAsync(() -> cartRepository.delete(cart));
	}
}
