package com.campusretail.userservice.service;

import com.campusretail.userservice.entity.User;
import com.campusretail.userservice.entity.UserRole;
import com.campusretail.userservice.repository.UserRepository;
import com.campusretail.userservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the service interface, using
 * dependency injection to use the repositorys to
 * develop the methods for the controller
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserRoleRepository userRoleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User getUserByName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User saveUser(User user) {
		user.setActive(1);
		UserRole role = userRoleRepository.findUserRoleByRoleName("User");
		user.setRole(role);
		return userRepository.save(user);
	}
}
