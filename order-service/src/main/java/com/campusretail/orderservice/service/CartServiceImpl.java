package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.domain.Product;
import com.campusretail.orderservice.domain.User;
import com.campusretail.orderservice.feignclient.ProductClient;
import com.campusretail.orderservice.feignclient.UserClient;
import com.campusretail.orderservice.repository.CartRepository;
import com.campusretail.orderservice.repository.ItemRepository;
import com.campusretail.orderservice.repository.ProductRepository;
import com.campusretail.orderservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.campusretail.orderservice.utilities.CartUtilities.getSubTotalForItem;

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
	private final UserRepository userRepository;
	private final UserClient userClient;
	private final ItemRepository itemRepository;
	private final ProductRepository productRepository;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository,
	                       ProductClient productClient, ProductRepository productRepository,
	                       UserRepository userRepository, UserClient userClient,
	                       ItemRepository itemRepository) {
		this.cartRepository = cartRepository;
		this.productClient = productClient;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.userClient = userClient;
		this.itemRepository = itemRepository;
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<Cart> addItemToCart(Long userId, Long productId, Integer quantity) throws InterruptedException, ExecutionException {
		Cart cart = getCart(userId).get();
		List<Item> items = cart.getItems();

		Product product = productClient.getProductById(productId);
		BigDecimal subTotal = getSubTotalForItem(product, quantity);
		Item item = new Item(quantity, subTotal, product);

		if (checkIfItemExists(items, productId).get())
			items.forEach(i -> {
				if (i.getProduct().getId().equals(productId)) {
					i.setQuantity(quantity);
					i.setSubTotal(subTotal);
				}
				
			});
		else {
			productRepository.save(product);
			itemRepository.save(item);
			items.add(item);
		}

		cart.setItems(items);
		return CompletableFuture.completedFuture(cartRepository.save(cart));
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<Cart> getCart(Long userId) {
		Optional<Cart> optionalCart = cartRepository.findCartByUserId(userId);
		if (optionalCart.isPresent())
			return CompletableFuture.completedFuture(optionalCart.get());

		User user = userClient.getUserById(userId);
		Cart cart = new Cart();
		cart.setUser(userRepository.save(user));
		return CompletableFuture.completedFuture(cartRepository.save(cart));
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
	public CompletableFuture<Boolean> checkIfItemExists(List<Item> items, Long productId) {
		return CompletableFuture.completedFuture(items
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
