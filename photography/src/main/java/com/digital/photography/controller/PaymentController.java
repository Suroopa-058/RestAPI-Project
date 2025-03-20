package com.digital.photography.controller;

import com.digital.photography.entities.Payment;
import com.digital.photography.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ✅ Get all payments with pagination & sorting
    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Page<Payment>> getPaymentsPagedAndSorted(
            @PathVariable int page,
            @PathVariable int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Payment> payments = paymentService.getPaymentsPagedAndSorted(page, size, sortBy, direction);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // ✅ Get all payments without pagination
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    // ✅ Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Get payments by Booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getPaymentsByBookingId(@PathVariable int bookingId) {
        List<Payment> payments = paymentService.getPaymentsByBookingId(bookingId);

        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no payments found
        }

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // ✅ Get payments by Photo ID
    @GetMapping("/photo/{photoId}")
    public ResponseEntity<List<Payment>> getPaymentsByPhotoId(@PathVariable int photoId) {
        List<Payment> payments = paymentService.getPaymentsByPhotoId(photoId);

        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no payments found
        }

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // ✅ Get payments after a certain date
    @GetMapping("/after-date")
    public ResponseEntity<List<Payment>> getPaymentsAfterDate(@RequestParam String date) {
        LocalDateTime parsedDate = LocalDateTime.parse(date);
        List<Payment> payments = paymentService.getPaymentsAfterDate(parsedDate);

        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 if no payments found
        }

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // ✅ Get total amount by Booking ID
    @GetMapping("/total/{bookingId}")
    public ResponseEntity<Double> getTotalAmountByBookingId(@PathVariable int bookingId) {
        return new ResponseEntity<>(paymentService.getTotalAmountByBookingId(bookingId), HttpStatus.OK);
    }

    // ✅ Create a new payment
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.CREATED);
    }

    // ✅ Update payment type
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePaymentType(@PathVariable int id, @RequestParam String type) {
        paymentService.updatePaymentType(id, type);
        return ResponseEntity.ok("Payment type updated successfully.");
    }

    // ✅ Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        try {
            paymentService.deletePayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
