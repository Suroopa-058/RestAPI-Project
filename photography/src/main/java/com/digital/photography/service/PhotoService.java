package com.digital.photography.service;

import com.digital.photography.entities.Photo;
import com.digital.photography.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepo photoRepo;

    // Create Photo
    public Photo createPhoto(Photo photo) {
        return photoRepo.save(photo);
    }

    // Get All Photos
    public List<Photo> getAllPhotos() {
        return photoRepo.findAll();
    }

    // Get Photo by ID
    public Optional<Photo> getPhotoById(int id) {
        return photoRepo.findById(id);
    }

    // Get Photos by Portfolio ID
    public List<Photo> getPhotosByPortfolioId(int portfolioId) {
        return photoRepo.findByPortfolioId(portfolioId);
    }

    // Get Photos in a Price Range
    public List<Photo> getPhotosByPriceRange(double minPrice, double maxPrice) {
        return photoRepo.findByPriceRange(minPrice, maxPrice);
    }

    // Update Photo Price
    public void updatePhotoPrice(int id, double newPrice) {
        photoRepo.updatePhotoPrice(id, newPrice);
    }

    // Delete Photo
    public void deletePhoto(int id) {
        photoRepo.deletePhotoById(id);
    }

    public Page<Photo> getPhotosPagedAndSorted(int page, int size, String sortBy, String direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPhotosPagedAndSorted'");
    }
}
