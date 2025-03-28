package com.digital.photography.repository;

import com.digital.photography.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {

    // Find all bookings by Client ID
    @Query("SELECT b FROM Booking b WHERE b.clientId = :clientId")
    List<Booking> findByClientId(@Param("clientId") int clientId);

    // Find all bookings by Photographer ID
    @Query("SELECT b FROM Booking b WHERE b.photographerId = :photographerId")
    List<Booking> findByPhotographerId(@Param("photographerId") int photographerId);

    // Find bookings by status (Pending, Confirmed, Cancelled)
    @Query("SELECT b FROM Booking b WHERE b.status = :status")
    List<Booking> findByStatus(@Param("status") String status);

    // Find bookings after a certain date
    @Query("SELECT b FROM Booking b WHERE b.date >= :date")
    List<Booking> findBookingsAfterDate(@Param("date") LocalDate date);

    // Update booking status
    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = :status WHERE b.id = :id")
    void updateBookingStatus(@Param("id") int id, @Param("status") String status);

    // Delete booking by ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.id = :id")
    void deleteBookingById(@Param("id") int id);

    List<Booking> findBookingsByPhotographerId(int photographerId);

    List<Booking> findBookingsByClientId(int clientId);
}
