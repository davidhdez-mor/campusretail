package com.campusretail.userservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.campusretail.userservice.entity.User;

/**
 * Interface to define the 
 * methods for posterior use 
 * in the controllers
 */

public interface UserService {
    
    CompletableFuture<List<User>> getAllUsers();
    CompletableFuture<User> getUserById(Long id);
    CompletableFuture<User> getUserByName(String userName);
    CompletableFuture<User> saveUser(User user);
}
