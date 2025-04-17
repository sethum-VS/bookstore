package com.CSA.exception;

/**
 * Exception thrown when a cart is not found in the system.
 */
public class CartNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public CartNotFoundException(String message) {
        super(message);
    }
    
    public CartNotFoundException(int customerId) {
        super("Cart not found for customer with ID: " + customerId);
    }
}
