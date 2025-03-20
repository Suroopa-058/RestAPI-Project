package com.digital.photography.service;

import com.digital.photography.entities.Portfolio;
import com.digital.photography.repository.PortfolioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepo portfolioRepo;



    // Create Portfolio
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepo.save(portfolio);
    }

    // Get All Portfolios
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepo.findAll();
    }

    // Get Portfolio by ID
    public Optional<Portfolio> getPortfolioById(int id) {
        return portfolioRepo.findById(id);
    }

    // Get Portfolios by Photographer ID
    public List<Portfolio> getPortfoliosByPhotographer(int photographerId) {
        return portfolioRepo.findByPhotographerId(photographerId);
    }

    // Update Portfolio Title
    public void updatePortfolioTitle(int id, String newTitle) {
        portfolioRepo.updatePortfolioTitle(id, newTitle);
    }

    // Delete Portfolio
    public void deletePortfolio(int id) {
        portfolioRepo.deleteById(id);
    }

    // Get Paginated Portfolios
    public Page<Portfolio> getPortfoliosPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return portfolioRepo.findAll(pageable);
    }

    // Get Sorted Portfolios by a specific field
    public List<Portfolio> sortPortfolios(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
        return portfolioRepo.findAll(sort);
    }
}
