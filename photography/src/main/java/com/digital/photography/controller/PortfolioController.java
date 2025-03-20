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
    public ResponseEntity<Page<Portfolio>> getPortfoliosPaged(
            @PathVariable int page,
            @PathVariable int size) {
        
        Page<Portfolio> portfolios = portfolioService.getPortfoliosPaged(page, size);
        return new ResponseEntity<>(portfolios, HttpStatus.OK);
    }

    // Get all portfolios sorted by a specific field
    @GetMapping("/sort/{field}/{direction}")
    public ResponseEntity<List<Portfolio>> sortPortfolios(@PathVariable String field, @PathVariable String direction) {
        return ResponseEntity.ok(portfolioService.sortPortfolios(field, direction));
    }
    

    // Get all portfolios
    @GetMapping
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        return new ResponseEntity<>(portfolioService.getAllPortfolios(), HttpStatus.OK);
    }

    // Get portfolio by ID
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable int id) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolioById(id);
        return portfolio.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get portfolios by photographer ID
    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<Portfolio>> getPortfoliosByPhotographer(@PathVariable int photographerId) {
        List<Portfolio> portfolios = portfolioService.getPortfoliosByPhotographer(photographerId);

        if (portfolios.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no portfolios found
        }

        return new ResponseEntity<>(portfolios, HttpStatus.OK);
    }

    // Create a new portfolio
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio) {
        return new ResponseEntity<>(portfolioService.createPortfolio(portfolio), HttpStatus.CREATED);
    }

    // Update an existing portfolio title
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePortfolioTitle(@PathVariable int id, @RequestParam String title) {
        portfolioService.updatePortfolioTitle(id, title);
        return ResponseEntity.ok("Portfolio title updated successfully.");
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
}
