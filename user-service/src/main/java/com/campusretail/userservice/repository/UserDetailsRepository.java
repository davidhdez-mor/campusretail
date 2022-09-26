package com.campusretail.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campusretail.userservice.entity.UserDetails;

/**
 * Repository interface to have all the tools
 * of the JPA repository for the UserDetails class
 */

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
