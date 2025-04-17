package com.CSA.resource;

import com.CSA.model.Customer;
import com.CSA.storage.DataStore;
import com.CSA.LoggerUtil.LoggerUtil;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * REST Resource for Customer management operations.
 * Provides endpoints for creating, retrieving, updating, and deleting customers.
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    /**
     * Creates a new customer.
     * 
     * @param customer The customer data from request body
     * @return 201 Created with customer information or error response
     */
    @POST
    public Response createCustomer(Customer customer) {
        LoggerUtil.logInfo("Received request to create a new customer");
        
        // Validate required fields
        if (customer == null) {
            LoggerUtil.logWarning("Customer creation failed: Customer data is null");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Customer data is required\"}").build();
        }
        
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            LoggerUtil.logWarning("Customer creation failed: Name is required");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Name is required\"}").build();
        }
        
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            LoggerUtil.logWarning("Customer creation failed: Email is required");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Email is required\"}").build();
        }
        
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            LoggerUtil.logWarning("Customer creation failed: Password is required");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Password is required\"}").build();
        }
        
        // Check if email is already in use
        if (DataStore.emailToCustomerIdMap.containsKey(customer.getEmail())) {
            LoggerUtil.logWarning("Customer creation failed: Email already exists: " + customer.getEmail());
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"Email already in use\"}").build();
        }
        
        // Generate ID and add to data store
        int customerId = DataStore.getNextCustomerId();
        customer.setId(customerId);
        
        DataStore.customers.put(customerId, customer);
        DataStore.emailToCustomerIdMap.put(customer.getEmail(), customerId);
        
        LoggerUtil.logInfo("Customer created successfully with ID: " + customerId);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }
    
    /**
     * Lists all customers.
     * 
     * @return List of all customers
     */
    @GET
    public Response getAllCustomers() {
        LoggerUtil.logInfo("Fetching all customers");
        
        List<Customer> customerList = new ArrayList<>(DataStore.customers.values());
        return Response.ok(customerList).build();
    }
    
    /**
     * Gets a customer by ID.
     * 
     * @param id The customer ID
     * @return Customer information or 404 if not found
     */
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        LoggerUtil.logInfo("Fetching customer with ID: " + id);
        
        Customer customer = DataStore.customers.get(id);
        if (customer != null) {
            LoggerUtil.logInfo("Customer found with ID: " + id);
            return Response.ok(customer).build();
        } else {
            LoggerUtil.logWarning("Customer not found with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Customer not found\"}").build();
        }
    }
    
    /**
     * Updates an existing customer.
     * 
     * @param id The customer ID to update
     * @param updatedCustomer The updated customer data
     * @return Updated customer or 404 if not found
     */
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        LoggerUtil.logInfo("Updating customer with ID: " + id);
        
        if (updatedCustomer == null) {
            LoggerUtil.logWarning("Customer update failed: Updated data is null");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Customer data is required\"}").build();
        }
        
        Customer existingCustomer = DataStore.customers.get(id);
        if (existingCustomer == null) {
            LoggerUtil.logWarning("Customer update failed: ID not found: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Customer not found\"}").build();
        }
        
        // Validate inputs
        if (updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            LoggerUtil.logWarning("Customer update failed: Name is required");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Name is required\"}").build();
        }
        
        if (updatedCustomer.getEmail() == null || updatedCustomer.getEmail().trim().isEmpty()) {
            LoggerUtil.logWarning("Customer update failed: Email is required");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Email is required\"}").build();
        }
        
        if (updatedCustomer.getPassword() == null || updatedCustomer.getPassword().trim().isEmpty()) {
            LoggerUtil.logWarning("Customer update failed: Password is required");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Password is required\"}").build();
        }
        
        // If email is changing, ensure new email is not already in use by another customer
        String oldEmail = existingCustomer.getEmail();
        String newEmail = updatedCustomer.getEmail();
        
        if (!oldEmail.equals(newEmail) && DataStore.emailToCustomerIdMap.containsKey(newEmail)) {
            Integer existingId = DataStore.emailToCustomerIdMap.get(newEmail);
            if (!existingId.equals(id)) {
                LoggerUtil.logWarning("Customer update failed: New email already in use: " + newEmail);
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\": \"Email already in use by another customer\"}").build();
            }
        }
        
        // Update the customer
        updatedCustomer.setId(id);
        DataStore.customers.put(id, updatedCustomer);
        
        // Update email map if email changed
        if (!oldEmail.equals(newEmail)) {
            DataStore.emailToCustomerIdMap.remove(oldEmail);
            DataStore.emailToCustomerIdMap.put(newEmail, id);
            LoggerUtil.logInfo("Updated customer email mapping from " + oldEmail + " to " + newEmail);
        }
        
        LoggerUtil.logInfo("Customer updated successfully: " + id);
        return Response.ok(updatedCustomer).build();
    }
    
    /**
     * Deletes a customer by ID.
     * 
     * @param id The customer ID to delete
     * @return 204 No Content if successful, 404 if not found
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        LoggerUtil.logInfo("Deleting customer with ID: " + id);
        
        Customer customer = DataStore.customers.get(id);
        if (customer != null) {
            // Remove from both maps
            String email = customer.getEmail();
            DataStore.customers.remove(id);
            DataStore.emailToCustomerIdMap.remove(email);
            
            LoggerUtil.logInfo("Customer deleted successfully: " + id);
            return Response.noContent().build();
        } else {
            LoggerUtil.logWarning("Customer deletion failed: ID not found: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Customer not found\"}").build();
        }
    }
}
