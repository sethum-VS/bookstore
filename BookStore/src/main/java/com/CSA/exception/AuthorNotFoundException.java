package com.CSA.exception;

/**
 * Exception thrown when an author is not found in the system.
 */
public class AuthorNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public AuthorNotFoundException(String message) {
        super(message);
    }
    
    public AuthorNotFoundException(int authorId) {
        super("Author not found with ID: " + authorId);
    }
}
