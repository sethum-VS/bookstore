package com.CSA.model;

/**
 * Represents a customer in the bookstore system.
 */
public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
    }

    /**
     * Parameterized constructor for Customer.
     * 
     * @param id The unique identifier of the customer
     * @param name The name of the customer
     * @param email The email address of the customer
     * @param password The password of the customer
     */
    public Customer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the ID of the customer.
     * 
     * @return The customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the customer.
     * 
     * @param id The customer ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the customer.
     * 
     * @return The customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * 
     * @param name The customer name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the customer.
     * 
     * @return The customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the customer.
     * 
     * @param email The customer email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the customer.
     * 
     * @return The customer password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the customer.
     * 
     * @param password The customer password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the Customer object.
     * 
     * @return String representation of the Customer
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
