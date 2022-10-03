package com.campusretail.orderservice.repository;

import com.campusretail.orderservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
