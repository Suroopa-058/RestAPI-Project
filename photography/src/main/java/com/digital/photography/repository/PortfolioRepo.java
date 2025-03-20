package com.digital.photography.repository;

import com.digital.photography.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer> {

    // Find Portfolio by Photographer ID
    @Query("SELECT p FROM Portfolio p WHERE p.photographerId = :photographerId")
    List<Portfolio> findByPhotographerId(@Param("photographerId") int photographerId);

    // Search Portfolio by Title (Partial Matching)
    @Query("SELECT p FROM Portfolio p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Portfolio> searchByTitle(@Param("title") String title);

    // Update Portfolio Title
    @Query("UPDATE Portfolio p SET p.title = :title WHERE p.id = :id")
    void updatePortfolioTitle(@Param("id") int id, @Param("title") String title);

    // Delete Portfolio by ID
    @Query("DELETE FROM Portfolio p WHERE p.id = :id")
    void deletePortfolioById(@Param("id") int id);
}
