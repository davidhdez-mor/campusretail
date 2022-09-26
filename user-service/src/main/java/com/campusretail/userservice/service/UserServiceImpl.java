package com.campusretail.userservice.service;

import com.campusretail.userservice.entity.User;
import com.campusretail.userservice.entity.UserRole;
import com.campusretail.userservice.repository.UserRepository;
import com.campusretail.userservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
	
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Async("threadPoolExecutor")
	public CompletableFuture<List<User>> getAllUsers() {
		return CompletableFuture.completedFuture(userRepository.findAll());
	}

	@Override
	@Async("threadPoolExecutor")
	public CompletableFuture<User> getUserById(Long id) {
		return CompletableFuture.completedFuture(userRepository.findById(id).orElse(null));
	}

	@Override
	@Async("threadPoolExecutor")
	public CompletableFuture<User> getUserByName(String userName) {
		return CompletableFuture.completedFuture(userRepository.findByUserName(userName));
	}

	@Override
	@Async("threadPoolExecutor")
	public CompletableFuture<User> saveUser(User user) {
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user.setActive(1);
		UserRole role = userRoleRepository.findUserRoleByRoleName("User");
		user.setRole(role);
		return CompletableFuture.completedFuture(userRepository.save(user));
	}
}
