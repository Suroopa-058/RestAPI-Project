package com.digital.photography.service;

import com.digital.photography.entities.Portfolio;
import com.digital.photography.repository.PortfolioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepo portfolioRepo;

    // Create a new portfolio
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepo.save(portfolio);
    }

    // Get all portfolios with pagination
    public Page<Portfolio> getAllPortfolios(int page, int size) {
        return portfolioRepo.findAll(PageRequest.of(page, size));
    }

    // Get portfolio by ID
    public Optional<Portfolio> getPortfolioById(int id) {
        return portfolioRepo.findById(id);
    }

    // Update an existing portfolio
    public Portfolio updatePortfolio(int id, Portfolio portfolioDetails) {
        return portfolioRepo.findById(id).map(portfolio -> {
            portfolio.setTitle(portfolioDetails.getTitle());
            portfolio.setDescription(portfolioDetails.getDescription());
            portfolio.setPhotographerId(portfolioDetails.getPhotographerId());
            return portfolioRepo.save(portfolio);
        }).orElseThrow(() -> new RuntimeException("Portfolio not found with id " + id));
    }

    // Delete a portfolio
    public void deletePortfolio(int id) {
        if (portfolioRepo.existsById(id)) {
            portfolioRepo.deleteById(id);
        } else {
            throw new RuntimeException("Portfolio not found with id " + id);
        }
    }

    // Get portfolios by photographer ID
    public List<Portfolio> getPortfoliosByPhotographer(int photographerId) {
        return portfolioRepo.findByPhotographerId(photographerId);
    }

    // Sort portfolios by a specific field
    public List<Portfolio> sortPortfolios(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
        return portfolioRepo.findAll(sort);
    }
}
