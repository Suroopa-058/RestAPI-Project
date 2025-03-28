package com.digital.photography.service;

import com.digital.photography.entities.Payment;
import com.digital.photography.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    // Get all payments with pagination
    public Page<Payment> getAllPayments(int page, int size) {
        return paymentRepo.findAll(PageRequest.of(page, size));
    }

    // Get payment by ID
    public Optional<Payment> getPaymentById(int id) {
        return paymentRepo.findById(id);
    }

    // Create a new payment
    public Payment createPayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    // Update an existing payment type
    public Payment updatePaymentType(int id, String type) {
        return paymentRepo.findById(id).map(payment -> {
            payment.setType(type);
            return paymentRepo.save(payment);
        }).orElseThrow(() -> new RuntimeException("Payment not found with id " + id));
    }

    // Delete a payment
    public void deletePayment(int id) {
        if (paymentRepo.existsById(id)) {
            paymentRepo.deleteById(id);
        } else {
            throw new RuntimeException("Payment not found with id " + id);
        }
    }

    // Get payments by booking ID
    public List<Payment> getPaymentsByBookingId(int bookingId) {
        return paymentRepo.findByBookingId(bookingId);
    }

    // Get payments by photo ID
    public List<Payment> getPaymentsByPhotoId(int photoId) {
        return paymentRepo.findByPhotoId(photoId);
    }

    // Get payments after a certain date
    public List<Payment> getPaymentsAfterDate(LocalDateTime date) {
        return paymentRepo.findPaymentsAfterDate(date);
    }

    // Get total amount by booking ID
    public Double getTotalAmountByBookingId(int bookingId) {
        return paymentRepo.getTotalAmountByBookingId(bookingId);
    }

    // Sort payments by a specific field
    public List<Payment> sortPayments(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
        return paymentRepo.findAll(sort);
    }
}
