package com.digital.photography.service;

import com.digital.photography.entities.Photo;
import com.digital.photography.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepo photoRepo;

    // Get all photos with pagination
    public Page<Photo> getAllPhotos(int page, int size) {
        return photoRepo.findAll(PageRequest.of(page, size));
    }

    // Get photo by ID
    public Optional<Photo> getPhotoById(int id) {
        return photoRepo.findById(id);
    }

    // Create a new photo
    public Photo createPhoto(Photo photo) {
        return photoRepo.save(photo);
    }

    // Update an existing photo's price
    public Photo updatePhotoPrice(int id, double newPrice) {
        return photoRepo.findById(id).map(photo -> {
            photo.setPrice(newPrice);
            return photoRepo.save(photo);
        }).orElseThrow(() -> new RuntimeException("Photo not found with id " + id));
    }

    // Delete a photo
    public void deletePhoto(int id) {
        if (photoRepo.existsById(id)) {
            photoRepo.deleteById(id);
        } else {
            throw new RuntimeException("Photo not found with id " + id);
        }
    }

    // Get photos by portfolio ID
    public List<Photo> getPhotosByPortfolioId(int portfolioId) {
        return photoRepo.findByPortfolioId(portfolioId);
    }

    // Get photos in a price range
    public List<Photo> getPhotosByPriceRange(double minPrice, double maxPrice) {
        return photoRepo.findByPriceRange(minPrice, maxPrice);
    }

    // Sort photos by a specific field
    public List<Photo> sortPhotos(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
        return photoRepo.findAll(sort);
    }
}
