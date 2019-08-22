package com.ct.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.beans.Beans;

/**
 * This is essentially a Java <code>{@link Beans}</code> class that represents
 * the events model. It has seven main properties, namely:
 * <ul>
 * <li>eventId
 * <li>eventType
 * <li>eventName
 * <li>venue
 * <li>dateTime
 * <li>ticketPrice
 * <li>remark
 * </ul>
 * This class provides the setters as well as the getters for these properties.
 *
 * @author admin
 */
public class EventModel {

    /**
     * The id property of the event model.
     */
    private final String eventId;
    /**
     * The type property of the event. It should be only one of these two:
     * <ul>
     * <li>Concert
     * <li>Theater
     * </ul>
     */
    private String eventType;
    /**
     * The name property of the event.
     */
    private String eventName;
    /**
     * The venue property of the event.
     */
    private String venue;
    /**
     * The date and time property of the event.
     */
    private LocalDateTime dateTime;
    /**
     * The price property of the event.
     */
    private BigDecimal ticketPrice;
    /**
     * The remark property of the event.
     */
    private String remark;

    /**
     * Default constructor for an event model. It sets all the properties that
     * one could expect from an event.
     *
     * @param eventId     The id property of the event model.
     * @param eventType   The type property of the event. It should be only one
     *                    of these two: (1) <strong>Concert</strong>, or (2)
     * <strong>Theater</strong>.
     * @param eventName   The name property of the event.
     * @param venue       The venue property of the event.
     * @param dateTime    The date and time property of the event.
     * @param ticketPrice The price property of the event.
     * @param remark      The remark property of the event.
     */
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

    /**
     * Retrieve the id property of the event model.
     *
     * @return the id property.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Retrieve The type property of the event. It should be only one
     * of these two: (1) <strong>Concert</strong>, or (2)
     * <strong>Theater</strong>.
     *
     * @return the type property of the event.
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the type property of the event. It should be only one
     * of these two: (1) <strong>Concert</strong>, or (2)
     * <strong>Theater</strong>.
     *
     * @param eventType the new type property of the event.
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Retrieve the name property of the event.
     *
     * @return the name property of the event.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Set the name property of the event.
     *
     * @param eventName the new name property of the event.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Retrieves the venue property of the event.
     *
     * @return the venue property of the event.
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Sets the venue property of the event.
     *
     * @param venue the new venue property of the event.
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * Retrieves the date and time property of the event.
     *
     * @return the date and time property of the event.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time property of the event.
     *
     * @param dateTime the new date and time property of the event.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the price property of the event.
     *
     * @return the price property of the event.
     */
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Sets the price property of the event.
     *
     * @param ticketPrice the new price property of the event.
     */
    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Retrieves the remark property of the event.
     *
     * @return the remark property of the event.
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the remark property of the event.
     *
     * @param remark the new remark property of the event.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.eventId);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return eventId + " - " + eventName;
    }

}
