package com.digital.photography.controller;

import com.digital.photography.entities.Photo;
import com.digital.photography.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // Get all photos with pagination
    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Page<Photo>> getAllPhotos(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(photoService.getAllPhotos(page, size), HttpStatus.OK);
    }

    // Get photo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable int id) {
        Optional<Photo> photo = photoService.getPhotoById(id);
        return photo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new photo
    @PostMapping
    public ResponseEntity<Photo> createPhoto(@RequestBody Photo photo) {
        return new ResponseEntity<>(photoService.createPhoto(photo), HttpStatus.CREATED);
    }

    // Update photo price
    @PutMapping("/{id}/price")
    public ResponseEntity<Photo> updatePhotoPrice(@PathVariable int id, @RequestBody double newPrice) {
        try {
            Photo updatedPhoto = photoService.updatePhotoPrice(id, newPrice);
            return ResponseEntity.ok(updatedPhoto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a photo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable int id) {
        try {
            photoService.deletePhoto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get photos by portfolio ID
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<Photo>> getPhotosByPortfolioId(@PathVariable int portfolioId) {
        List<Photo> photos = photoService.getPhotosByPortfolioId(portfolioId);

        if (photos.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no photos found
        }

        return ResponseEntity.ok(photos);
    }

    // Get photos by price range
    @GetMapping("/price-range")
    public ResponseEntity<List<Photo>> getPhotosByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        List<Photo> photos = photoService.getPhotosByPriceRange(minPrice, maxPrice);

        if (photos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(photos);
    }

    // Sort photos by a specific field
    @GetMapping("/sort/{field}/{direction}")
    public ResponseEntity<List<Photo>> sortPhotos(@PathVariable String field, @PathVariable String direction) {
        return ResponseEntity.ok(photoService.sortPhotos(field, direction));
    }
}
