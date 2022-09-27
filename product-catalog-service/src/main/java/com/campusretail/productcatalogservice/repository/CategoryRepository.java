package com.campusretail.productcatalogservice.repository;

import com.campusretail.productcatalogservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findAllByCategory(String category);
}
