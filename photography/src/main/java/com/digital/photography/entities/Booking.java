package com.digital.photography.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int clientId;
    private int photographerId;
    private LocalDate date;
    private String status; // Pending, Confirmed, Cancelled

    // Constructors
    public Booking() {
    }

    public Booking(int id, int clientId, int photographerId, LocalDate date, String status) {
        this.id = id;
        this.clientId = clientId;
        this.photographerId = photographerId;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(int clientId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClient'");
    }

    public Object getBookingDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingDate'");
    }

    public void setBookingDate(Object bookingDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBookingDate'");
    }

    public int getClient() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClient'");
    }

    public int getPhotographer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPhotographer'");
    }
}
