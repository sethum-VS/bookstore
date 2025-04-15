package com.CSA.resource;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.model.Book;
import com.CSA.storage.DataStore;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

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
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Book title is required\"}")
                .build();
        }
        if (book.getAuthorId() <= 0) {
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Valid author ID is required\"}")
                .build();
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"ISBN is required\"}")
                .build();
        }
        if (book.getPublicationYear() <= 0) {
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Valid publication year is required\"}")
                .build();
        }
        if (book.getPrice() <= 0) {
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Valid price is required\"}")
                .build();
        }
        if (book.getStockQuantity() < 0) {
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Stock quantity cannot be negative\"}")
                .build();
        }

        // Check if the author exists
        if (!DataStore.authors.containsKey(book.getAuthorId())) {
            return Response.status(Status.NOT_FOUND)
                .entity("{\"error\": \"Author not found with ID: " + book.getAuthorId() + "\"}")
                .build();
        }

        // Assign a unique book ID
        int bookId = DataStore.getNextBookId();
        book.setId(bookId);

        // Save the book to DataStore
        DataStore.books.put(bookId, book);

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
            return Response.status(Status.NOT_FOUND)
                .entity("{\"error\": \"Book not found with ID: " + id + "\"}")
                .build();
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
            return Response.status(Status.NOT_FOUND)
                .entity("{\"error\": \"Book not found with ID: " + id + "\"}")
                .build();
        }
        
        // Validate the updated book data
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            LoggerUtil.logWarning("Failed to update book: Title is required");
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Book title is required\"}")
                .build();
        }
        if (book.getAuthorId() <= 0) {
            LoggerUtil.logWarning("Failed to update book: Valid author ID is required");
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Valid author ID is required\"}")
                .build();
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            LoggerUtil.logWarning("Failed to update book: ISBN is required");
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"ISBN is required\"}")
                .build();
        }
        if (book.getPublicationYear() <= 0) {
            LoggerUtil.logWarning("Failed to update book: Valid publication year is required");
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Valid publication year is required\"}")
                .build();
        }
        if (book.getPrice() <= 0) {
            LoggerUtil.logWarning("Failed to update book: Valid price is required");
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Valid price is required\"}")
                .build();
        }
        if (book.getStockQuantity() < 0) {
            LoggerUtil.logWarning("Failed to update book: Stock quantity cannot be negative");
            return Response.status(Status.BAD_REQUEST)
                .entity("{\"error\": \"Stock quantity cannot be negative\"}")
                .build();
        }
        
        // Check if the author exists
        if (!DataStore.authors.containsKey(book.getAuthorId())) {
            LoggerUtil.logWarning("Failed to update book: Author not found with ID: " + book.getAuthorId());
            return Response.status(Status.NOT_FOUND)
                .entity("{\"error\": \"Author not found with ID: " + book.getAuthorId() + "\"}")
                .build();
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
            return Response.status(Status.NOT_FOUND)
                .entity("{\"error\": \"Book not found with ID: " + id + "\"}")
                .build();
        }
        
        DataStore.books.remove(id);
        LoggerUtil.logInfo("Successfully deleted book with ID: " + id);
        
        return Response.status(Status.NO_CONTENT).build();
    }
}
