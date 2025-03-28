package com.digital.photography.controller;

import com.digital.photography.entities.Booking;
import com.digital.photography.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Get all bookings by pagination
    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Page<Booking>> getAllBookings(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(bookingService.getAllBookings(page, size), HttpStatus.OK);
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.createBooking(booking), HttpStatus.CREATED);
    }

    // Update an existing booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable int id, @RequestBody Booking bookingDetails) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable int id) {
        try {
            bookingService.deleteBooking(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Sort bookings by a specific field
    @GetMapping("/sort/{field}/{direction}")
    public ResponseEntity<List<Booking>> sortBookings(@PathVariable String field, @PathVariable String direction) {
        return ResponseEntity.ok(bookingService.sortBookings(field, direction));
    }

    // Get bookings by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable int userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no bookings found
        }
        
        return ResponseEntity.ok(bookings);
    }

    // Get bookings by photographer ID
    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<Booking>> getBookingsByPhotographerId(@PathVariable int photographerId) {
        List<Booking> bookings = bookingService.getBookingsByPhotographerId(photographerId);
        
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no bookings found
        }
        
        return ResponseEntity.ok(bookings);
    }
}
