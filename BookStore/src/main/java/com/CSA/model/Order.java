package com.CSA.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an order in the bookstore system.
 */
public class Order {
    private int id;
    private int customerId;
    private Map<Integer, Integer> items; // Maps bookId → quantity
    private double totalAmount;
    private LocalDateTime orderDate;

    /**
     * Default constructor for Order.
     */
    public Order() {
        this.items = new HashMap<>();
        this.orderDate = LocalDateTime.now();
    }

    /**
     * Parameterized constructor for Order.
     * 
     * @param id The unique identifier of the order
     * @param customerId The ID of the customer who placed the order
     * @param items The map of book IDs to quantities
     * @param totalAmount The total amount of the order
     * @param orderDate The date and time when the order was placed
     */
    public Order(int id, int customerId, Map<Integer, Integer> items, double totalAmount, LocalDateTime orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    /**
     * Gets the ID of the order.
     * 
     * @return The order ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     * 
     * @param id The order ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the customer ID associated with this order.
     * 
     * @return The customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID associated with this order.
     * 
     * @param customerId The customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets all items in the order.
     * 
     * @return A map of book IDs to quantities
     */
    public Map<Integer, Integer> getItems() {
        return items;
    }

    /**
     * Sets the items in the order.
     * 
     * @param items The map of book IDs to quantities to set
     */
    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }

    /**
     * Gets the total amount of the order.
     * 
     * @return The total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the order.
     * 
     * @param totalAmount The total amount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets the date and time when the order was placed.
     * 
     * @return The order date and time
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date and time when the order was placed.
     * 
     * @param orderDate The order date and time to set
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns a string representation of the Order object.
     * 
     * @return String representation of the Order
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                '}';
    }
}
