package com.digital.photography.repository;

import com.digital.photography.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {

    // Find all payments by Booking ID
    @Query("SELECT p FROM Payment p WHERE p.bookingId = :bookingId")
    List<Payment> findByBookingId(@Param("bookingId") int bookingId);

    // Find all payments by Photo ID
    @Query("SELECT p FROM Payment p WHERE p.photoId = :photoId")
    List<Payment> findByPhotoId(@Param("photoId") int photoId);

    // Find all payments made after a certain date
    @Query("SELECT p FROM Payment p WHERE p.transactionDate >= :date")
    List<Payment> findPaymentsAfterDate(@Param("date") LocalDateTime date);

    // Find total amount paid for a specific booking
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.bookingId = :bookingId")
    Double getTotalAmountByBookingId(@Param("bookingId") int bookingId);

    // Update payment type by ID
    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.type = :type WHERE p.id = :id")
    void updatePaymentType(@Param("id") int id, @Param("type") String type);

    // Delete payment by ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.id = :id")
    void deletePaymentById(@Param("id") int id);
}
