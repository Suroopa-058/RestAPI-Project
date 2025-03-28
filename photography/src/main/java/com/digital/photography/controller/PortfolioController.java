package com.digital.photography.controller;

import com.digital.photography.entities.Portfolio;
import com.digital.photography.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    // Get all portfolios with pagination
    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Page<Portfolio>> getAllPortfolios(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(portfolioService.getAllPortfolios(page, size), HttpStatus.OK);
    }

    // Get portfolio by ID
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable int id) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolioById(id);
        return portfolio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new portfolio
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio) {
        return new ResponseEntity<>(portfolioService.createPortfolio(portfolio), HttpStatus.CREATED);
    }

    // Update an existing portfolio
    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable int id, @RequestBody Portfolio portfolioDetails) {
        try {
            Portfolio updatedPortfolio = portfolioService.updatePortfolio(id, portfolioDetails);
            return ResponseEntity.ok(updatedPortfolio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a portfolio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable int id) {
        try {
            portfolioService.deletePortfolio(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Sort portfolios by a specific field
    @GetMapping("/sort/{field}/{direction}")
    public ResponseEntity<Object> sortPortfolios(@PathVariable String field, @PathVariable String direction) {
        return ResponseEntity.ok(portfolioService.sortPortfolios(field, direction));
    }

    // Get portfolios by photographer ID
    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<Portfolio>> getPortfoliosByPhotographer(@PathVariable int photographerId) {
        List<Portfolio> portfolios = portfolioService.getPortfoliosByPhotographer(photographerId);

        if (portfolios.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no portfolios found
        }

        return ResponseEntity.ok(portfolios);
    }
}
