package com.digital.photography.service;

import com.digital.photography.entities.Payment;
import com.digital.photography.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    // Create Payment
    public Payment createPayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    // Get All Payments
    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    // Get Payment by ID
    public Optional<Payment> getPaymentById(int id) {
        return paymentRepo.findById(id);
    }

    // Get Payments by Booking ID
    public List<Payment> getPaymentsByBookingId(int bookingId) {
        return paymentRepo.findByBookingId(bookingId);
    }

    // Get Payments by Photo ID
    public List<Payment> getPaymentsByPhotoId(int photoId) {
        return paymentRepo.findByPhotoId(photoId);
    }

    // Get Payments After a Certain Date
    public List<Payment> getPaymentsAfterDate(LocalDateTime date) {
        return paymentRepo.findPaymentsAfterDate(date);
    }

    // Get Total Amount by Booking ID
    public Double getTotalAmountByBookingId(int bookingId) {
        return paymentRepo.getTotalAmountByBookingId(bookingId);
    }

    // Update Payment Type
    public void updatePaymentType(int id, String type) {
        paymentRepo.updatePaymentType(id, type);
    }

    // Delete Payment
    public void deletePayment(int id) {
        paymentRepo.deletePaymentById(id);
    }

    public Page<Payment> getPaymentsPagedAndSorted(int page, int size, String sortBy, String direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentsPagedAndSorted'");
    }
}
