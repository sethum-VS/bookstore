package com.CSA.exception.mappers;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.exception.InvalidInputException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception mapper for InvalidInputException.
 * Maps the exception to a 400 Bad Request HTTP response.
 */
@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException exception) {
        LoggerUtil.logWarning("Invalid input exception: " + exception.getMessage());
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "InvalidInputException");
        errorResponse.put("message", exception.getMessage());
        
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
