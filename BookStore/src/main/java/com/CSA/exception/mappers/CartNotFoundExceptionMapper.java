package com.CSA.exception.mappers;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.exception.CartNotFoundException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception mapper for CartNotFoundException.
 * Maps the exception to a 404 Not Found HTTP response.
 */
@Provider
public class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException> {

    @Override
    public Response toResponse(CartNotFoundException exception) {
        LoggerUtil.logWarning("Cart not found exception: " + exception.getMessage());
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "CartNotFoundException");
        errorResponse.put("message", exception.getMessage());
        
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
