package com.CSA.resource;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.exception.BookNotFoundException;
import com.CSA.exception.CartNotFoundException;
import com.CSA.exception.CustomerNotFoundException;
import com.CSA.exception.InvalidInputException;
import com.CSA.model.Cart;
import com.CSA.model.Customer;
import com.CSA.storage.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Resource class that handles cart-related operations for customers.
 */
@Path("/customers")
public class CartResource {

    /**
     * Adds an item to a customer's cart.
     *
     * @param customerId The ID of the customer
     * @param cartItem The item to add to the cart
     * @return Response with the updated cart or error message
     */    @POST
    @Path("/{customerId}/cart/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemToCart(@PathParam("customerId") int customerId, CartItem cartItem) {
        LoggerUtil.logInfo("Request to add item to cart for customer ID: " + customerId);
        
        // Validate customer exists
        Customer customer = DataStore.customers.get(customerId);
        if (customer == null) {
            LoggerUtil.logWarning("Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Validate book exists
        if (!DataStore.books.containsKey(cartItem.getBookId())) {
            LoggerUtil.logWarning("Book not found with ID: " + cartItem.getBookId());
            throw new BookNotFoundException(cartItem.getBookId());
        }
        
        // Validate quantity
        if (cartItem.getQuantity() <= 0) {
            LoggerUtil.logWarning("Invalid quantity: " + cartItem.getQuantity());
            throw new InvalidInputException("Quantity must be greater than 0");
        }
        
        // Get or create cart for the customer
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null) {
            cart = new Cart(customerId);
            DataStore.carts.put(customerId, cart);
            LoggerUtil.logInfo("Created new cart for customer ID: " + customerId);
        }
        
        // Add item to cart
        cart.addItem(cartItem.getBookId(), cartItem.getQuantity());
        LoggerUtil.logInfo("Added item to cart: bookId=" + cartItem.getBookId() + ", quantity=" + cartItem.getQuantity());
        
        return Response.ok(cart).build();
    }
    
    /**
     * Gets a customer's cart.
     *
     * @param customerId The ID of the customer
     * @return Response with the cart or error message
     */    @GET
    @Path("/{customerId}/cart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") int customerId) {
        LoggerUtil.logInfo("Request to view cart for customer ID: " + customerId);
        
        // Validate customer exists
        Customer customer = DataStore.customers.get(customerId);
        if (customer == null) {
            LoggerUtil.logWarning("Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get cart or return empty if not found
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null) {
            LoggerUtil.logInfo("Cart not found for customer ID: " + customerId + ". Returning empty cart.");
            cart = new Cart(customerId);
        }
        
        return Response.ok(cart).build();
    }
    
    /**
     * Updates the quantity of an item in a customer's cart.
     *
     * @param customerId The ID of the customer
     * @param bookId The ID of the book to update
     * @param cartItem The updated item info
     * @return Response with the updated cart or error message
     */
    @PUT
    @Path("/{customerId}/cart/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCartItem(
            @PathParam("customerId") int customerId,
            @PathParam("bookId") int bookId,
            CartItem cartItem) {
        
        LoggerUtil.logInfo("Request to update item in cart: customerId=" + customerId + ", bookId=" + bookId);
          // Validate customer exists
        Customer customer = DataStore.customers.get(customerId);
        if (customer == null) {
            LoggerUtil.logWarning("Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Validate book exists
        if (!DataStore.books.containsKey(bookId)) {
            LoggerUtil.logWarning("Book not found with ID: " + bookId);
            throw new BookNotFoundException(bookId);
        }
        
        // Get cart
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null || !cart.getItems().containsKey(bookId)) {
            LoggerUtil.logWarning("Item not found in cart: customerId=" + customerId + ", bookId=" + bookId);
            throw new CartNotFoundException("Item not found in cart for customer ID: " + customerId);
        }
        
        // Validate quantity
        if (cartItem.getQuantity() <= 0) {
            LoggerUtil.logWarning("Invalid quantity: " + cartItem.getQuantity());
            throw new InvalidInputException("Quantity must be greater than 0");
        }
        
        // Update item quantity
        cart.updateItem(bookId, cartItem.getQuantity());
        LoggerUtil.logInfo("Updated item in cart: customerId=" + customerId + 
                ", bookId=" + bookId + ", quantity=" + cartItem.getQuantity());
        
        return Response.ok(cart).build();
    }
    
    /**
     * Removes an item from a customer's cart.
     *
     * @param customerId The ID of the customer
     * @param bookId The ID of the book to remove
     * @return Response indicating success or error
     */
    @DELETE
    @Path("/{customerId}/cart/items/{bookId}")
    public Response removeCartItem(
            @PathParam("customerId") int customerId,
            @PathParam("bookId") int bookId) {
        
        LoggerUtil.logInfo("Request to remove item from cart: customerId=" + customerId + ", bookId=" + bookId);
          // Validate customer exists
        Customer customer = DataStore.customers.get(customerId);
        if (customer == null) {
            LoggerUtil.logWarning("Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get cart
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null || !cart.getItems().containsKey(bookId)) {
            LoggerUtil.logWarning("Item not found in cart: customerId=" + customerId + ", bookId=" + bookId);
            throw new CartNotFoundException("Item not found in cart for customer ID: " + customerId);
        }
        
        // Remove item from cart
        cart.removeItem(bookId);
        LoggerUtil.logInfo("Removed item from cart: customerId=" + customerId + ", bookId=" + bookId);
        
        return Response.status(Status.NO_CONTENT).build();
    }
    
    /**
     * Inner class representing a cart item for JSON serialization/deserialization.
     */
    public static class CartItem {
        private int bookId;
        private int quantity;
        
        public CartItem() {
            // Default constructor for JAX-RS
        }
        
        public int getBookId() {
            return bookId;
        }
        
        public void setBookId(int bookId) {
            this.bookId = bookId;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
