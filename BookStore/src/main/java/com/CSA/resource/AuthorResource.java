package com.CSA.resource;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.model.Author;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Resource class for handling Author-related operations.
 */
@Path("/authors")
public class AuthorResource {

    /**
     * Adds a new author to the system.
     * 
     * @param author The author object to be added
     * @return Response with the newly created author
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAuthor(Author author) {
        // Validate the author object
        if (author.getName() == null || author.getName().isEmpty()) {
            LoggerUtil.logWarning("Failed to add author: Name cannot be null or empty");
            return Response.status(Status.BAD_REQUEST)
                    .entity("Author name cannot be null or empty")
                    .build();
        }
        
        if (author.getBiography() == null || author.getBiography().isEmpty()) {
            LoggerUtil.logWarning("Failed to add author: Biography cannot be null or empty");
            return Response.status(Status.BAD_REQUEST)
                    .entity("Author biography cannot be null or empty")
                    .build();
        }
        
        // Generate a unique author ID
        int authorId = DataStore.getNextAuthorId();
        author.setId(authorId);
        
        // Save the new author to DataStore
        DataStore.authors.put(authorId, author);
        LoggerUtil.logInfo("Successfully added new author with ID: " + authorId);
        
        // Return 201 Created with the newly created author
        return Response.status(Status.CREATED)
                .entity(author)
                .build();
    }
    
    /**
     * Retrieves all authors from the system.
     * 
     * @return Response with a list of all authors
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        // Retrieve all authors from DataStore
        List<Author> authorList = new ArrayList<>(DataStore.authors.values());
        LoggerUtil.logInfo("Retrieved all authors from the system");
        
        // Return 200 OK with the list of authors
        return Response.status(Status.OK)
                .entity(authorList)
                .build();
    }
    
    /**
     * Retrieves a specific author by ID.
     * 
     * @param id The ID of the author to retrieve
     * @return Response with the author or 404 if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") int id) {
        LoggerUtil.logInfo("Attempting to retrieve author with ID: " + id);
        
        Author author = DataStore.authors.get(id);
        if (author == null) {
            LoggerUtil.logWarning("Author not found with ID: " + id);
            return Response.status(Status.NOT_FOUND)
                    .entity("Author not found with ID: " + id)
                    .build();
        }
        
        LoggerUtil.logInfo("Retrieved author with ID: " + id);
        return Response.status(Status.OK)
                .entity(author)
                .build();
    }
    
    /**
     * Updates an existing author's information.
     * 
     * @param id The ID of the author to update
     * @param updatedAuthor The updated author data
     * @return Response with the updated author or appropriate error status
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        LoggerUtil.logInfo("Attempting to update author with ID: " + id);
        
        // Check if author exists
        if (!DataStore.authors.containsKey(id)) {
            LoggerUtil.logWarning("Failed to update: Author not found with ID: " + id);
            return Response.status(Status.NOT_FOUND)
                    .entity("Author not found with ID: " + id)
                    .build();
        }
        
        // Validate the updated author data
        if (updatedAuthor.getName() == null || updatedAuthor.getName().isEmpty()) {
            LoggerUtil.logWarning("Failed to update author: Name cannot be null or empty");
            return Response.status(Status.BAD_REQUEST)
                    .entity("Author name cannot be null or empty")
                    .build();
        }
        
        if (updatedAuthor.getBiography() == null || updatedAuthor.getBiography().isEmpty()) {
            LoggerUtil.logWarning("Failed to update author: Biography cannot be null or empty");
            return Response.status(Status.BAD_REQUEST)
                    .entity("Author biography cannot be null or empty")
                    .build();
        }
        
        // Preserve the author ID
        updatedAuthor.setId(id);
        
        // Update the author in DataStore
        DataStore.authors.put(id, updatedAuthor);
        LoggerUtil.logInfo("Successfully updated author with ID: " + id);
        
        return Response.status(Status.OK)
                .entity(updatedAuthor)
                .build();
    }
    
    /**
     * Deletes an author from the system by ID.
     * 
     * @param id The ID of the author to delete
     * @return Response with 204 No Content if successful, or 404 if author not found
     */
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        LoggerUtil.logInfo("Attempting to delete author with ID: " + id);
        
        if (!DataStore.authors.containsKey(id)) {
            LoggerUtil.logWarning("Failed to delete: Author not found with ID: " + id);
            return Response.status(Status.NOT_FOUND)
                    .entity("Author not found with ID: " + id)
                    .build();
        }
        
        DataStore.authors.remove(id);
        LoggerUtil.logInfo("Successfully deleted author with ID: " + id);
        
        return Response.status(Status.NO_CONTENT).build();
    }
    
    /**
     * Retrieves all books written by a specific author.
     * 
     * @param id The ID of the author
     * @return Response with a list of books or appropriate error status
     */
    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByAuthor(@PathParam("id") int id) {
        LoggerUtil.logInfo("Attempting to retrieve books by author with ID: " + id);
        
        // Check if author exists
        if (!DataStore.authors.containsKey(id)) {
            LoggerUtil.logWarning("Failed to retrieve books: Author not found with ID: " + id);
            return Response.status(Status.NOT_FOUND)
                    .entity("Author not found with ID: " + id)
                    .build();
        }
        
        // Filter books by author ID
        List<Book> authorBooks = DataStore.books.values().stream()
                .filter(book -> book.getAuthorId() == id)
                .collect(Collectors.toList());
        
        LoggerUtil.logInfo("Retrieved " + authorBooks.size() + " books for author with ID: " + id);
        return Response.status(Status.OK)
                .entity(authorBooks)
                .build();
    }
}
