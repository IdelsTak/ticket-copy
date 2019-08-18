/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.models;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Hiram K.
 */
public class CustomerModel {

    private final int id;
    private EventModel event;
    private String name;
    private int phone;
    private int numberOfTickets;
    private BigDecimal totalCostOfTickets;
    private boolean paid;
    private boolean issued;
    private LocalDate bookingDate;

    public CustomerModel(
            int id,
            EventModel event,
            String name,
            int phone,
            int numberOfTickets,
            BigDecimal totalCostOfTickets,
            boolean paid,
            boolean issued,
            LocalDate bookingDate) {
        this.id = id;
        this.event = event;
        this.name = name;
        this.phone = phone;
        this.numberOfTickets = numberOfTickets;
        this.totalCostOfTickets = totalCostOfTickets;
        this.paid = paid;
        this.issued = issued;
        this.bookingDate = bookingDate;
    }

    public int getId() {
        return id;
    }

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public BigDecimal getTotalCostOfTickets() {
        return totalCostOfTickets;
    }

    public void setTotalCostOfTickets(BigDecimal totalCostOfTickets) {
        this.totalCostOfTickets = totalCostOfTickets;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomerModel other = (CustomerModel) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "CustomerModel{"
                + "id="
                + id
                + ", event="
                + event
                + ", name="
                + name
                + ", phone="
                + phone
                + ", numberOfTickets="
                + numberOfTickets
                + ", totalCostOfTickets="
                + totalCostOfTickets
                + ", paid="
                + paid
                + ", issued="
                + issued
                + ", bookingDate="
                + bookingDate
                + '}';
    }

}
