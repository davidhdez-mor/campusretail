package com.campusretail.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campusretail.userservice.entity.UserRole;

/**
 * Repository interface to have all the tools
 * of the JPA repository for the UserRole class
 * also adding the method to find by role name 
 */

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findUserRoleByRoleName(String roleName);
}
