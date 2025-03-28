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

    // Get all payments by pagination
    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Page<Payment>> getAllPayments(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(paymentService.getAllPayments(page, size), HttpStatus.OK);
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new payment
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.CREATED);
    }

    // Update payment type
    @PutMapping("/{id}/type")
    public ResponseEntity<Payment> updatePaymentType(@PathVariable int id, @RequestBody String type) {
        try {
            Payment updatedPayment = paymentService.updatePaymentType(id, type);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        try {
            paymentService.deletePayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get payments by booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getPaymentsByBookingId(@PathVariable int bookingId) {
        List<Payment> payments = paymentService.getPaymentsByBookingId(bookingId);
        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(payments);
    }

    // Get payments by photo ID
    @GetMapping("/photo/{photoId}")
    public ResponseEntity<List<Payment>> getPaymentsByPhotoId(@PathVariable int photoId) {
        List<Payment> payments = paymentService.getPaymentsByPhotoId(photoId);
        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(payments);
    }

    // Get payments made after a certain date
    @GetMapping("/after/{date}")
    public ResponseEntity<List<Payment>> getPaymentsAfterDate(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Payment> payments = paymentService.getPaymentsAfterDate(dateTime);
        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(payments);
    }

    // Get total amount by booking ID
    @GetMapping("/total/{bookingId}")
    public ResponseEntity<Double> getTotalAmountByBookingId(@PathVariable int bookingId) {
        Double totalAmount = paymentService.getTotalAmountByBookingId(bookingId);
        return ResponseEntity.ok(totalAmount);
    }

    // Get sorted payments
    @GetMapping("/sort/{field}/{direction}")
    public ResponseEntity<Object> sortPayments(@PathVariable String field, @PathVariable String direction) {
        return ResponseEntity.ok(paymentService.sortPayments(field, direction));
    }
}
