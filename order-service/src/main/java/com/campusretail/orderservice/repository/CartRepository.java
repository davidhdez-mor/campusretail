package com.campusretail.orderservice.repository;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface with all the needed
 * methods for the Cart implementation
 * extending from JPA to have all the
 * default methods
 */
//TODO: check if the methods works as intended
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//	void addItemToCart(String key, Object item);
//	List<Item> getCart(String key, Class type);
//	void deleteItemFromCart(String key, Object item);
//	void deleteCart(String key);
}
