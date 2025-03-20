package com.digital.photography.controller;

import com.digital.photography.entities.Booking;
import com.digital.photography.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create Booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    // Retrieve All Bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Retrieve Booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Retrieve Bookings by Client ID
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Booking>> getBookingsByClientId(@PathVariable int clientId) {
        return ResponseEntity.ok(bookingService.getBookingsByClientId(clientId));
    }

    // Retrieve Bookings by Photographer ID
    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<Booking>> getBookingsByPhotographerId(@PathVariable int photographerId) {
        return ResponseEntity.ok(bookingService.getBookingsByPhotographerId(photographerId));
    }

    // Retrieve Bookings by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    // Retrieve Bookings After a Certain Date
    @GetMapping("/after-date")
    public ResponseEntity<List<Booking>> getBookingsAfterDate(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return ResponseEntity.ok(bookingService.getBookingsAfterDate(parsedDate));
    }

    // Update Booking Status
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateBookingStatus(@PathVariable int id, @RequestParam String status) {
        bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok("Booking status updated successfully.");
    }

    // Delete Booking
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully.");
    }
}
