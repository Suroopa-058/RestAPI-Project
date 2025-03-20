package com.digital.photography.service;

import com.digital.photography.entities.Booking;
import com.digital.photography.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    // Create a new Booking
    public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    // Get all Bookings
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // Get Booking by ID
    public Optional<Booking> getBookingById(int id) {
        return bookingRepo.findById(id);
    }

    // Get Bookings by Client ID
    public List<Booking> getBookingsByClientId(int clientId) {
        return bookingRepo.findByClientId(clientId);
    }

    // Get Bookings by Photographer ID
    public List<Booking> getBookingsByPhotographerId(int photographerId) {
        return bookingRepo.findByPhotographerId(photographerId);
    }

    // Get Bookings by Status
    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepo.findByStatus(status);
    }

    // Get Bookings After a Certain Date
    public List<Booking> getBookingsAfterDate(LocalDate date) {
        return bookingRepo.findBookingsAfterDate(date);
    }

    // Update Booking Status
    public void updateBookingStatus(int id, String status) {
        bookingRepo.updateBookingStatus(id, status);
    }

    // Delete Booking
    public void deleteBooking(int id) {
        bookingRepo.deleteBookingById(id);
    }

    public Page<Booking> getBookingsPagedAndSorted(int page, int size, String sortBy, String direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsPagedAndSorted'");
    }

    public Object getTotalAmountByBookingId(int bookingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalAmountByBookingId'");
    }
}
