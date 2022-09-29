package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Order;
import com.campusretail.orderservice.domain.User;
import com.campusretail.orderservice.exception.CartNotFoundException;
import com.campusretail.orderservice.exception.OrderNotFoundException;
import com.campusretail.orderservice.feignclient.UserClient;
import com.campusretail.orderservice.repository.CartRepository;
import com.campusretail.orderservice.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
	public CompletableFuture<Optional<Order>> saveOrder(Long cartId) {
		Optional<Cart> optionalCart = cartRepository.findById(cartId);
		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();
			User user = cart.getUser();
			Order order = orderRepository.save(createOrder(cart, user));
			cartRepository.delete(cart);
			return CompletableFuture.completedFuture(orderRepository.findById(order.getId()));
		}
		throw new CartNotFoundException("Cart not found");
	}
}
