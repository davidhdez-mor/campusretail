package com.campusretail.productcatalogservice.repository;

import com.campusretail.productcatalogservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
