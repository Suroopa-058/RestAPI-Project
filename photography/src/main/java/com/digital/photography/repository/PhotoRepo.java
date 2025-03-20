package com.digital.photography.repository;

import com.digital.photography.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Integer> {

    // Find all photos by Portfolio ID
    @Query("SELECT p FROM Photo p WHERE p.portfolioId = :portfolioId")
    List<Photo> findByPortfolioId(@Param("portfolioId") int portfolioId);

    // Find all photos within a price range
    @Query("SELECT p FROM Photo p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Photo> findByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    // Update photo price by ID
    @Modifying
    @Transactional
    @Query("UPDATE Photo p SET p.price = :price WHERE p.id = :id")
    void updatePhotoPrice(@Param("id") int id, @Param("price") double price);

    // Delete photo by ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Photo p WHERE p.id = :id")
    void deletePhotoById(@Param("id") int id);
}
