package com.CSA.exception;

/**
 * Exception thrown when a customer is not found in the system.
 */
public class CustomerNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public CustomerNotFoundException(String message) {
        super(message);
    }
    
    public CustomerNotFoundException(int customerId) {
        super("Customer not found with ID: " + customerId);
    }
}
