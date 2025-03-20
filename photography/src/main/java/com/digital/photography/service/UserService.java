package com.digital.photography.service;

import com.digital.photography.entities.User;
import com.digital.photography.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users with pagination
    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    // Get user by ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(int id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setFirstname(userDetails.getFirstname());
            user.setLastname(userDetails.getLastname());
            user.setEmail(userDetails.getEmail());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // Delete a user
    public void deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    // Sort users by a specific field (e.g., "firstname", "lastname", "email")
    public List<User> sortUsers(String field) {
        return userRepository.findAll(Sort.by(field));
    }

    // Get users by role
    public List<User> getUsersByRole(String role) {
        return userRepository.getUsersByRole(role);
    }

    public List<User> sortUsers(String field, String direction) {
        // Check if direction is "desc", otherwise default to ascending
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
        
        // Fetch sorted users
        return userRepository.findAll(sort);
    }

    
}
