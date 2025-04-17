package com.CSA.exception;

/**
 * Exception thrown when a book is out of stock.
 */
public class OutOfStockException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public OutOfStockException(String message) {
        super(message);
    }
    
    public OutOfStockException(int bookId, int requestedQuantity, int availableQuantity) {
        super("Book with ID: " + bookId + " is out of stock. Requested: " + 
              requestedQuantity + ", Available: " + availableQuantity);
    }
}
