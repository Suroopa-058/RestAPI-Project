package com.digital.photography.repository;

import com.digital.photography.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Insert new user (Native Query)
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user (firstname, lastname, email, role) VALUES (:firstname, :lastname, :email, :role)", nativeQuery = true)
    void createUser(@Param("firstname") String firstname, 
                    @Param("lastname") String lastname, 
                    @Param("email") String email, 
                    @Param("role") String role);

    // Get all users
    @Query("SELECT u FROM User u")
    List<User> getAllUsers();

    // Get user by ID
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findById(@Param("id") int id);

    // Get users by role
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> getUsersByRole(@Param("role") String role);

    // Update user details
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.firstname = :firstname, u.lastname = :lastname, u.email = :email, u.role = :role WHERE u.id = :id")
    int updateUser(@Param("id") int id, 
                   @Param("firstname") String firstname, 
                   @Param("lastname") String lastname, 
                   @Param("email") String email, 
                   @Param("role") String role);

    // Delete user by ID
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    
    void deleteUser(@Param("id") int id);

    // Sorting
    @Query("SELECT u FROM User u")
    List<User> findAllSorted(Sort sort);

    // Pagination
    @Query("SELECT u FROM User u")
    Page<User> findAllPaged(Pageable pageable);

    // Pagination with Sorting
    @Query("SELECT u FROM User u ORDER BY u.firstname ASC")
    Page<User> findAllPagedSorted(Pageable pageable);
}
