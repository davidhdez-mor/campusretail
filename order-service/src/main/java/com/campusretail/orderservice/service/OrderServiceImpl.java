package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.domain.Order;
import com.campusretail.orderservice.domain.User;
import com.campusretail.orderservice.repository.CartRepository;
import com.campusretail.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.campusretail.orderservice.utilities.OrderUtilities.createOrder;

/**
 * Implementation of all the
 * methods from the interface
 * of order to do all the actions
 * needed to make work the
 * controller
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository) {
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<Order> saveOrder(Long userId) {
		Optional<Cart> optionalCart = cartRepository.findCartByUserId(userId);
		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();
			User user = cart.getUser();
			List<Item> items = cart.getItems();
			LinkedList<Item> linkedItems = new LinkedList<>(items);
			Order order = orderRepository.save(createOrder(cart, linkedItems, user));
			
			items.clear();
			cart.setItems(items);
			cartRepository.save(cart);
			return CompletableFuture.completedFuture(order);
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<List<Order>> getOrders(Long userId) {
		return CompletableFuture.completedFuture(orderRepository.findAllByUserId(userId));
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<Order> getOrder(Long id, Long userId) {
		return CompletableFuture.completedFuture(orderRepository.findAllByUserId(userId)
				.stream()
				.filter(order -> order.getId().equals(id))
				.findAny()
				.orElse(null));
	}
}
