package com.CSA.exception.mappers;

import com.CSA.LoggerUtil.LoggerUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic exception mapper for catching any uncaught exceptions in the system.
 * Maps unexpected exceptions to a 500 Internal Server Error response.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        // Log the stack trace for debugging purposes
        LoggerUtil.logSevere("Unexpected error occurred: " + exception.getMessage());
        exception.printStackTrace();
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", "An unexpected error occurred.");
        
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
