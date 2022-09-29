package com.campusretail.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campusretail.userservice.entity.User;

import java.util.Optional;

/**
 * Repository interface to have all the tools
 * of the JPA repository for the User class,
 * also adding the method to find by username 
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
