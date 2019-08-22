package com.ct.models;

import java.beans.Beans;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This is essentially a Java <code>{@link Beans}</code> class that represents
 * the customer model. It has nine main properties, namely:
 * <ul>
 * <li>id
 * <li>event
 * <li>name
 * <li>phone
 * <li>numberOfTickets
 * <li>totalCostOfTickets
 * <li>paid
 * <li>issued
 * <li>bookingDate
 * </ul>
 * This class provides the setters as well as the getters for these properties.
 * <p>
 * In the domain, a customer usually makes a booking to attend an event.
 * Accordingly, this class contains an <code>{@link EventModel}</code> as one of
 * its properties to create a <code>one-to-many</code> relationship between an
 * event and a customer.
 *
 * @author admin
 */
public class CustomerModel {

    /**
     * The id property of a customer model. It's marked final so that client
     * classes don't change it. In essence, once a customer model is created,
     * it's id property shouldn't change during its lifetime.
     */
    private final int id;
    /**
     * The <code>{@link EventModel}</code> that is customer is linked to.
     */
    private EventModel event;
    /**
     * The name property of the customer model.
     */
    private String name;
    /**
     * The phone property of the customer model.
     */
    private int phone;
    /**
     * The number of tickets property of the customer model.
     */
    private int numberOfTickets;
    /**
     * The total cost of tickets property of the customer model. It should be
     * calculated as:
     * <p>
     * &nbsp;<code>total cost of tickets = no of tickets X ticket price</code>
     * <p>
     * Where:
     * the <code>ticket price</code> is retrieved from the customer model's
     * <code>{@link EventModel}</code> property.
     */
    private BigDecimal totalCostOfTickets;
    /**
     * The paid property of the customer model.
     */
    private boolean paid;
    /**
     * The issued property of the customer model.
     */
    private boolean issued;
    /**
     * The date of booking property of the customer property.
     */
    private LocalDate bookingDate;

    /**
     * Default constructor for a customer model. It sets all the properties that
     * one could expect from a customer.
     *
     * @param id                 the customer's id property.
     * @param event              the <code>{@link EventModel}</code> property.
     * @param name               the customer's name property.
     * @param phone              the customer's phone property.
     * @param numberOfTickets    the number of tickets that the customer has
     *                           bought.
     * @param totalCostOfTickets the total cost of the tickets that the customer
     *                           has bought.
     * @param paid               whether the customer has paid for the tickets.
     * @param issued             whether the customer has received the tickets
     *                           that he/she bought.
     * @param bookingDate        the date the customer booked for the tickets.
     */
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

    /**
     * Retrieves the customer's id property.
     *
     * @return the customer's id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the event that the customer seeks to attend.
     *
     * @return the event that the customer seeks to attend.
     */
    public EventModel getEvent() {
        return event;
    }

    /**
     * Sets the event that the customer will attend.
     *
     * @param event the new event that the customer will attend.
     */
    public void setEvent(EventModel event) {
        this.event = event;
    }

    /**
     * Retrieves the customer's name.
     *
     * @return the customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     *
     * @param name the new name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the customer's phone number.
     *
     * @return the customer's phone number.
     */
    public int getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone number.
     *
     * @param phone the new phone number of the customer.
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the number of tickets that the customer seeks to buy.
     *
     * @return the number of tickets that the customer seeks to buy.
     */
    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    /**
     * Sets the number of tickets that the customer seeks to buy.
     *
     * @param numberOfTickets the new number of tickets that the customer seeks
     *                        to buy.
     */
    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    /**
     * Retrieves the total cost of the tickets that the customer seeks to buy.
     *
     * @return the total cost of the tickets that the customer seeks to buy.
     */
    public BigDecimal getTotalCostOfTickets() {
        return totalCostOfTickets;
    }

    /**
     * Sets the total cost of the tickets that the customer seeks to buy.
     *
     * @param totalCostOfTickets the new total cost of the tickets that the
     *                           customer seeks to buy.
     */
    public void setTotalCostOfTickets(BigDecimal totalCostOfTickets) {
        this.totalCostOfTickets = totalCostOfTickets;
    }

    /**
     * Retrieves whether the customer has paid for an event.
     *
     * @return whether the customer has paid for an event.
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets whether the customer has paid for an event.
     *
     * @param paid the new value of whether the customer has paid for an event.
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Retrieves whether the customer has been issued with tickets for an event.
     *
     * @return whether the customer has been issued with tickets for an event.
     */
    public boolean isIssued() {
        return issued;
    }

    /**
     * Sets whether the customer will be issued with tickets for an event.
     *
     * @param issued the new value of whether the customer will be issued with
     *               tickets for an event.
     */
    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    /**
     * Retrieves the date the customer booked for an event.
     *
     * @return the date the customer booked for an event.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the date the customer booked for an event.
     *
     * @param bookingDate the new date the customer booked for an event.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
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
        final CustomerModel other = (CustomerModel) obj;
        return this.id == other.id;
    }

    /**
     * {@inheritDoc}
     */
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
