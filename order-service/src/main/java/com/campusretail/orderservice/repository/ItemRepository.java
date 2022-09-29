package com.campusretail.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campusretail.orderservice.domain.Item;

/**
 * Interface which extends from JPA
 * to have all the default methods
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
