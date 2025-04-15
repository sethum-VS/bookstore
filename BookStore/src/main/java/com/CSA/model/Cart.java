package com.CSA.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a shopping cart in the bookstore system.
 */
public class Cart {
    private int customerId;
    private Map<Integer, Integer> items; // Maps bookId → quantity

    /**
     * Default constructor for Cart.
     */
    public Cart() {
        this.items = new HashMap<>();
    }

    /**
     * Parameterized constructor for Cart.
     * 
     * @param customerId The ID of the customer who owns the cart
     */
    public Cart(int customerId) {
        this.customerId = customerId;
        this.items = new HashMap<>();
    }

    /**
     * Gets the customer ID associated with this cart.
     * 
     * @return The customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID associated with this cart.
     * 
     * @param customerId The customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Adds an item to the cart or increases its quantity if it already exists.
     * 
     * @param bookId The ID of the book to add
     * @param quantity The quantity to add
     */
    public void addItem(int bookId, int quantity) {
        if (quantity <= 0) {
            return; // Ignore non-positive quantities
        }
        
        this.items.put(bookId, this.items.getOrDefault(bookId, 0) + quantity);
    }

    /**
     * Updates the quantity of an item in the cart.
     * 
     * @param bookId The ID of the book to update
     * @param quantity The new quantity
     */
    public void updateItem(int bookId, int quantity) {
        if (quantity <= 0) {
            removeItem(bookId);
        } else {
            this.items.put(bookId, quantity);
        }
    }

    /**
     * Removes an item from the cart.
     * 
     * @param bookId The ID of the book to remove
     */
    public void removeItem(int bookId) {
        this.items.remove(bookId);
    }

    /**
     * Gets all items in the cart.
     * 
     * @return A map of book IDs to quantities
     */
    public Map<Integer, Integer> getItems() {
        return items;
    }

    /**
     * Returns a string representation of the Cart object.
     * 
     * @return String representation of the Cart
     */
    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + customerId +
                ", items=" + items +
                '}';
    }
}
