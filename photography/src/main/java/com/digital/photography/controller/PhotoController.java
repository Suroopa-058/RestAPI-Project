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

    // ✅ Get paginated & sorted photos
    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Page<Photo>> getPhotosPagedAndSorted(
            @PathVariable int page,
            @PathVariable int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Photo> photos = photoService.getPhotosPagedAndSorted(page, size, sortBy, direction);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    // ✅ Get all photos without pagination
    @GetMapping
    public ResponseEntity<List<Photo>> getAllPhotos() {
        return new ResponseEntity<>(photoService.getAllPhotos(), HttpStatus.OK);
    }

    // ✅ Get photo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable int id) {
        Optional<Photo> photo = photoService.getPhotoById(id);
        return photo.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Get photos by Portfolio ID
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<Photo>> getPhotosByPortfolioId(@PathVariable int portfolioId) {
        List<Photo> photos = photoService.getPhotosByPortfolioId(portfolioId);

        if (photos.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no photos found
        }

        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    // ✅ Get photos within a price range
    @GetMapping("/price-range")
    public ResponseEntity<List<Photo>> getPhotosByPriceRange(
            @RequestParam double min,
            @RequestParam double max) {

        List<Photo> photos = photoService.getPhotosByPriceRange(min, max);

        if (photos.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no photos found
        }

        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    // ✅ Create a new photo
    @PostMapping
    public ResponseEntity<Photo> createPhoto(@RequestBody Photo photo) {
        return new ResponseEntity<>(photoService.createPhoto(photo), HttpStatus.CREATED);
    }

    // ✅ Update photo price
    @PutMapping("/{id}/price")
    public ResponseEntity<String> updatePhotoPrice(@PathVariable int id, @RequestParam double price) {
        photoService.updatePhotoPrice(id, price);
        return ResponseEntity.ok("Photo price updated successfully.");
    }

    // ✅ Delete a photo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable int id) {
        try {
            photoService.deletePhoto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
