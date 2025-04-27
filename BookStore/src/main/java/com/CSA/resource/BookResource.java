package com.CSA.resource;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.exception.AuthorNotFoundException;
import com.CSA.exception.BookNotFoundException;
import com.CSA.exception.InvalidInputException;
import com.CSA.model.Book;
import com.CSA.storage.DataStore;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.Collection;

/**
 * Resource class for handling Book related operations.
 * Implements REST endpoints for book management in the bookstore system.
 */
@Path("/books")
public class BookResource {

    /**
     * Adds a new book to the system.
     * 
     * @param book The book object in JSON format
     * @return Response with the created book or error message
     */    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        // Validate the incoming book object
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidInputException("Book title is required");
        }
        if (book.getAuthorId() <= 0) {
            throw new InvalidInputException("Valid author ID is required");
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new InvalidInputException("ISBN is required");
        }        if (book.getPublicationYear() <= 0) {
            throw new InvalidInputException("Valid publication year is required");
        }
        // Check if publication year is in the future
        if (book.getPublicationYear() > java.time.Year.now().getValue()) {
            throw new InvalidInputException("Publication year cannot be in the future");
        }
        if (book.getPrice() <= 0) {
            throw new InvalidInputException("Valid price is required");
        }
        if (book.getStockQuantity() < 0) {
            throw new InvalidInputException("Stock quantity cannot be negative");
        }

        // Check if the author exists
        if (!DataStore.authors.containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException(book.getAuthorId());
        }

        // Assign a unique book ID
        int bookId = DataStore.getNextBookId();
        book.setId(bookId);

        // Save the book to DataStore
        DataStore.books.put(bookId, book);
        LoggerUtil.logInfo("Book added with ID: " + bookId);

        // Return 201 Created with the saved book
        return Response.status(Status.CREATED)
            .entity(book)
            .build();
    }

    /**
     * Retrieves all books from the system.
     * 
     * @return Response with the list of all books
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        Collection<Book> allBooks = DataStore.books.values();
        LoggerUtil.logInfo("Retrieved all books from the system");
        return Response.ok(allBooks).build();
    }

    /**
     * Retrieves a specific book by its ID.
     * 
     * @param id The ID of the book to retrieve
     * @return Response with the book or 404 if not found
     */    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") int id) {
        LoggerUtil.logInfo("Attempting to retrieve book with ID: " + id);
        
        Book book = DataStore.books.get(id);
        if (book == null) {
            LoggerUtil.logWarning("Book not found with ID: " + id);
            throw new BookNotFoundException(id);
        }
        
        LoggerUtil.logInfo("Retrieved book with ID: " + id);
        return Response.ok(book).build();
    }

    /**
     * Updates an existing book's information.
     * 
     * @param id The ID of the book to update
     * @param book The updated book data
     * @return Response with the updated book or appropriate error status
     */    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book book) {
        LoggerUtil.logInfo("Attempting to update book with ID: " + id);
        
        // Check if book exists
        if (!DataStore.books.containsKey(id)) {
            LoggerUtil.logWarning("Failed to update: Book not found with ID: " + id);
            throw new BookNotFoundException(id);
        }
        
        // Validate the updated book data
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            LoggerUtil.logWarning("Failed to update book: Title is required");
            throw new InvalidInputException("Book title is required");
        }
        if (book.getAuthorId() <= 0) {
            LoggerUtil.logWarning("Failed to update book: Valid author ID is required");
            throw new InvalidInputException("Valid author ID is required");
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            LoggerUtil.logWarning("Failed to update book: ISBN is required");
            throw new InvalidInputException("ISBN is required");
        }
        if (book.getPublicationYear() <= 0) {
            LoggerUtil.logWarning("Failed to update book: Valid publication year is required");
            throw new InvalidInputException("Valid publication year is required");
        }
        // Check if publication year is in the future
        if (book.getPublicationYear() > java.time.Year.now().getValue()) {
            LoggerUtil.logWarning("Failed to update book: Publication year cannot be in the future");
            throw new InvalidInputException("Publication year cannot be in the future");
        }
        if (book.getPrice() <= 0) {
            LoggerUtil.logWarning("Failed to update book: Valid price is required");
            throw new InvalidInputException("Valid price is required");
        }
        if (book.getStockQuantity() < 0) {
            LoggerUtil.logWarning("Failed to update book: Stock quantity cannot be negative");
            throw new InvalidInputException("Stock quantity cannot be negative");
        }
        
        // Check if the author exists
        if (!DataStore.authors.containsKey(book.getAuthorId())) {
            LoggerUtil.logWarning("Failed to update book: Author not found with ID: " + book.getAuthorId());
            throw new AuthorNotFoundException(book.getAuthorId());
        }
        
        // Preserve the book ID
        book.setId(id);
        
        // Update the book in DataStore
        DataStore.books.put(id, book);
        LoggerUtil.logInfo("Successfully updated book with ID: " + id);
        
        return Response.ok(book).build();
    }

    /**
     * Deletes a book from the system by its ID.
     * 
     * @param id The ID of the book to delete
     * @return Response with 204 No Content if successful, or 404 if book not found
     */    
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        LoggerUtil.logInfo("Attempting to delete book with ID: " + id);
        
        if (!DataStore.books.containsKey(id)) {
            LoggerUtil.logWarning("Failed to delete: Book not found with ID: " + id);
            throw new BookNotFoundException(id);
        }
        
        DataStore.books.remove(id);
        LoggerUtil.logInfo("Successfully deleted book with ID: " + id);
        
        return Response.status(Status.NO_CONTENT).build();
    }
}
