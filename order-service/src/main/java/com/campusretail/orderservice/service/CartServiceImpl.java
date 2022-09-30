package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.domain.Product;
import com.campusretail.orderservice.exception.CartNotFoundException;
import com.campusretail.orderservice.feignclient.ProductClient;
import com.campusretail.orderservice.repository.CartRepository;
import com.campusretail.orderservice.utilities.CartUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
	@Async("asyncExecutor")
	public CompletableFuture<Cart> addItemToCart(Long userId, Long productId, Integer quantity) throws InterruptedException, ExecutionException {
		Optional<Cart> optionalCart = cartRepository.findCartByUserId(userId);
		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();
			List<Item> items = cart.getItems();

			Product product = productClient.getProductById(productId);
			Item item = new Item(quantity, CartUtilities.getSubTotalForItem(product, quantity), product);

			if (checkIfItemExists(cart, productId).get())
				items.forEach(i -> {
					if (i.getProduct().getId().equals(productId))
						i.setQuantity(quantity);
				});
			else
				items.add(item);

			cart.setItems(items);
			return CompletableFuture.completedFuture(cartRepository.save(cart));
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<Cart> getCart(Long userId) {
		Optional<Cart> optionalCart = cartRepository.findCartByUserId(userId);
		return optionalCart.map(CompletableFuture::completedFuture)
				.orElseGet(() -> CompletableFuture.completedFuture(null));
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<Cart> deleteItemFromCart(Long userId, Long productId) {
		Optional<Cart> optionalCart = cartRepository.findCartByUserId(userId);
		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();
			List<Item> items = cart.getItems();
			items.removeIf(item -> item.getProduct().getId().equals(productId));
			cart.setItems(items);
			return CompletableFuture.completedFuture(cartRepository.save(cart));
		}
		return CompletableFuture.completedFuture(null);
	}

	@Async("asyncExecutor")
	public CompletableFuture<Boolean> checkIfItemExists(Cart cart, Long productId) {
		return CompletableFuture.completedFuture(cart.getItems()
				.stream()
				.anyMatch(item -> item.getProduct().getId().equals(productId)));
	}
	
	@Override
	@Async("asyncExecutor")
	public CompletableFuture<Cart> deleteAllItemsFromCart(Long userId) {
		Optional<Cart> optionalCart = cartRepository.findCartByUserId(userId);
		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();
			List<Item> items = cart.getItems();
			items.clear();
			cart.setItems(items);
			return CompletableFuture.completedFuture(cartRepository.save(cart));
		}
		return CompletableFuture.completedFuture(null);
	}
}
