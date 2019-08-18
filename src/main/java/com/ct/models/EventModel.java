/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Hiram K.
 */
public class EventModel {

    private final String eventId;
    private String eventType;
    private String eventName;
    private String venue;
    private LocalDateTime dateTime;
    private BigDecimal ticketPrice;
    private String remark;

    public EventModel(
            String eventId,
            String eventType,
            String eventName,
            String venue,
            LocalDateTime dateTime,
            BigDecimal ticketPrice,
            String remark) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventName = eventName;
        this.venue = venue;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.remark = remark;
    }

    public String getEventId() {
        return eventId;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.eventId);
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
        final EventModel other = (EventModel) obj;
        return Objects.equals(this.eventId, other.eventId);
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
                + ", dateTime="
                + dateTime
                + ", ticketPrice="
                + ticketPrice
                + ", remark="
                + remark
                + '}';
    }

}
