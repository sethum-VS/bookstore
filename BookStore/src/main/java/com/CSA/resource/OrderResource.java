package com.CSA.resource;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.exception.BookNotFoundException;
import com.CSA.exception.CartNotFoundException;
import com.CSA.exception.CustomerNotFoundException;
import com.CSA.exception.InvalidInputException;
import com.CSA.exception.OutOfStockException;
import com.CSA.model.Book;
import com.CSA.model.Cart;
import com.CSA.model.Customer;
import com.CSA.model.Order;
import com.CSA.storage.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Resource class that handles order-related operations for customers.
 */
@Path("/customers")
public class OrderResource {

    /**
     * Creates a new order from the customer's cart.
     *
     * @param customerId The ID of the customer
     * @return Response with the created order or error message
     */
    @POST
    @Path("/{customerId}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("customerId") int customerId) {
        LoggerUtil.logInfo("Request to create order for customer ID: " + customerId);
          // Validate customer exists
        Customer customer = DataStore.customers.get(customerId);
        if (customer == null) {
            LoggerUtil.logWarning("Order creation failed: Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Check if the customer has a cart and it's not empty
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null) {
            LoggerUtil.logWarning("Order creation failed: Cart not found for customer ID: " + customerId);
            throw new CartNotFoundException(customerId);
        }
        
        if (cart.getItems().isEmpty()) {
            LoggerUtil.logWarning("Order creation failed: Cart is empty for customer ID: " + customerId);
            throw new InvalidInputException("Cannot create an order with an empty cart");
        }
        
        // Process the order
        Map<Integer, Integer> orderItems = new HashMap<>();
        double totalAmount = 0.0;
        
        // Validate books and check stock
        for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
            int bookId = entry.getKey();
            int quantity = entry.getValue();
              // Validate book exists
            Book book = DataStore.books.get(bookId);
            if (book == null) {
                LoggerUtil.logWarning("Order creation failed: Book not found with ID: " + bookId);
                throw new BookNotFoundException(bookId);
            }
            
            // Check if enough stock is available
            if (book.getQuantity() < quantity) {
                LoggerUtil.logWarning("Order creation failed: Insufficient stock for book ID: " + 
                        bookId + ", requested: " + quantity + ", available: " + book.getQuantity());
                throw new OutOfStockException(bookId, quantity, book.getQuantity());
            }
            
            // Add to order items
            orderItems.put(bookId, quantity);
            totalAmount += book.getPrice() * quantity;
            
            // Decrease book stock quantity
            book.setQuantity(book.getQuantity() - quantity);
            LoggerUtil.logInfo("Decreased stock for book ID: " + bookId + 
                    ", new stock: " + book.getQuantity());
        }
        
        // Create Order object
        int orderId = DataStore.getNextOrderId();
        LocalDateTime orderDate = LocalDateTime.now();
        Order order = new Order(orderId, customerId, orderItems, totalAmount, orderDate);
        
        // Store the order in DataStore
        List<Order> customerOrders = DataStore.ordersByCustomer.getOrDefault(customerId, new ArrayList<>());
        customerOrders.add(order);
        DataStore.ordersByCustomer.put(customerId, customerOrders);
        
        // Clear the cart after successful order
        DataStore.carts.remove(customerId);
        LoggerUtil.logInfo("Cart cleared for customer ID: " + customerId);
        
        LoggerUtil.logInfo("Order created successfully: ID=" + orderId + 
                ", customerId=" + customerId + ", totalAmount=" + totalAmount);
        
        return Response.status(Status.CREATED).entity(order).build();
    }
    
    /**
     * Gets all orders for a customer.
     *
     * @param customerId The ID of the customer
     * @return Response with the list of orders or error message
     */
    @GET
    @Path("/{customerId}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerOrders(@PathParam("customerId") int customerId) {
        LoggerUtil.logInfo("Request to get all orders for customer ID: " + customerId);
          // Validate customer exists
        Customer customer = DataStore.customers.get(customerId);
        if (customer == null) {
            LoggerUtil.logWarning("Get orders failed: Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get orders for the customer
        List<Order> orders = DataStore.ordersByCustomer.getOrDefault(customerId, new ArrayList<>());
        LoggerUtil.logInfo("Retrieved " + orders.size() + " orders for customer ID: " + customerId);
        
        return Response.ok(orders).build();
    }
    
    /**
     * Gets a specific order by ID for a customer.
     *
     * @param customerId The ID of the customer
     * @param orderId The ID of the order to retrieve
     * @return Response with the order or error message
     */
    @GET
    @Path("/{customerId}/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(
            @PathParam("customerId") int customerId,
            @PathParam("orderId") int orderId) {
        
        LoggerUtil.logInfo("Request to get order ID: " + orderId + " for customer ID: " + customerId);
          // Validate customer exists
        Customer customer = DataStore.customers.get(customerId);
        if (customer == null) {
            LoggerUtil.logWarning("Get order failed: Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get orders for the customer
        List<Order> orders = DataStore.ordersByCustomer.getOrDefault(customerId, new ArrayList<>());
        
        // Find the specific order
        Order order = null;
        for (Order o : orders) {
            if (o.getId() == orderId) {
                order = o;
                break;
            }
        }
        
        if (order == null) {
            LoggerUtil.logWarning("Order not found: ID=" + orderId + " for customer ID: " + customerId);
            throw new InvalidInputException("Order not found with ID: " + orderId);
        }
        
        LoggerUtil.logInfo("Retrieved order: ID=" + orderId + " for customer ID: " + customerId);
        return Response.ok(order).build();
    }
}
