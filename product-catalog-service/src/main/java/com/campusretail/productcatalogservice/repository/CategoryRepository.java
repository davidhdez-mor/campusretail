package com.campusretail.productcatalogservice.repository;

import com.campusretail.productcatalogservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for the Category entity
 * extending from JPARepository to have all the
 * default methods and creating a new method to
 * search by category
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findAllByCategory(String category);
}
