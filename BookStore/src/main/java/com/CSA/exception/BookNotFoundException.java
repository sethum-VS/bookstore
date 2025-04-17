package com.CSA.exception;

/**
 * Exception thrown when a book is not found in the system.
 */
public class BookNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public BookNotFoundException(String message) {
        super(message);
    }
    
    public BookNotFoundException(int bookId) {
        super("Book not found with ID: " + bookId);
    }
}
