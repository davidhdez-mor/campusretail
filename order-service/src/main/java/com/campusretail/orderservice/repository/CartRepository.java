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
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findCartByUserId(Long id);
}
