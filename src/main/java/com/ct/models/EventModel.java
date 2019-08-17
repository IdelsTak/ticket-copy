/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Hiram K.
 */
public class EventModel {

    private final String eventId;
    private String eventType;
    private String eventName;
    private String venue;
    private LocalDate date;
    private LocalTime time;
    private BigDecimal ticketPrice;
    private String remark;

    public EventModel(String eventId, String eventType, String eventName, String venue, LocalDate date, LocalTime time, BigDecimal ticketPrice, String remark) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.ticketPrice = ticketPrice;
        this.remark = remark;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "EventModel{"
                + "eventId="
                + eventId
                + ", eventType="
                + eventType
                + ", eventName="
                + eventName
                + ", venue="
                + venue
                + ", date="
                + date
                + ", time="
                + time
                + ", ticketPrice="
                + ticketPrice
                + ", remark="
                + remark
                + '}';
    }

}
