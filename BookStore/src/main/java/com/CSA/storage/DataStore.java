package com.CSA.storage;

import com.CSA.model.Author;
import com.CSA.model.Book;
import com.CSA.model.Cart;
import com.CSA.model.Customer;
import com.CSA.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central data store for the Bookstore application.
 * Since no external database is used, this class maintains all data in memory using collections.
 */
public class DataStore {
    
    // Static data structures to store application data
    public static HashMap<Integer, Book> books = new HashMap<>();
    public static HashMap<Integer, Author> authors = new HashMap<>();
    public static HashMap<Integer, Customer> customers = new HashMap<>();
    public static HashMap<String, Integer> emailToCustomerIdMap = new HashMap<>();
    public static HashMap<Integer, Cart> carts = new HashMap<>();
    public static HashMap<Integer, List<Order>> ordersByCustomer = new HashMap<>();
    
    // ID counters for auto-generation
    private static int nextBookId = 1;
    private static int nextAuthorId = 1;
    private static int nextCustomerId = 1;
    private static int nextOrderId = 1;
    
    /**
     * Returns the next available book ID and increments the counter.
     * @return A unique book ID
     */
    public static synchronized int getNextBookId() {
        return nextBookId++;
    }
    
    /**
     * Returns the next available author ID and increments the counter.
     * @return A unique author ID
     */
    public static synchronized int getNextAuthorId() {
        return nextAuthorId++;
    }
    
    /**
     * Returns the next available customer ID and increments the counter.
     * @return A unique customer ID
     */
    public static synchronized int getNextCustomerId() {
        return nextCustomerId++;
    }
    
    /**
     * Returns the next available order ID and increments the counter.
     * @return A unique order ID
     */
    public static synchronized int getNextOrderId() {
        return nextOrderId++;
    }
    
    
}
