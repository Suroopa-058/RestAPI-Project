package com.digital.photography.service;

import com.digital.photography.entities.Booking;
import com.digital.photography.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepository;

    // Get all bookings with pagination
    public Page<Booking> getAllBookings(int page, int size) {
        return bookingRepository.findAll(PageRequest.of(page, size));
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(int id) {
        return bookingRepository.findById(id);
    }

    // Create a new booking
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Update an existing booking
    public Booking updateBooking(int id, Booking bookingDetails) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setPhotographerId(bookingDetails.getPhotographer());
            booking.setClient(bookingDetails.getClient());
            booking.setBookingDate(bookingDetails.getBookingDate());
            booking.setStatus(bookingDetails.getStatus());
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    // Delete a booking
    public void deleteBooking(int id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else {
            throw new RuntimeException("Booking not found with id " + id);
        }
    }

    // Sort bookings by a specific field (e.g., "bookingDate", "status")
    public List<Booking> sortBookings(String field) {
        return bookingRepository.findAll(Sort.by(field));
    }

    // Sort bookings by a specific field and direction
    public List<Booking> sortBookings(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
        return bookingRepository.findAll(sort);
    }

    // Get bookings by Photographer ID
    public List<Booking> getBookingsByPhotographerId(int photographerId) {
        return bookingRepository.findBookingsByPhotographerId(photographerId);
    }

    // Get bookings by Client ID
    public List<Booking> getBookingsByClientId(int clientId) {
        return bookingRepository.findBookingsByClientId(clientId);
    }

    public List<Booking> getBookingsByUserId(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsByUserId'");
    }
}
