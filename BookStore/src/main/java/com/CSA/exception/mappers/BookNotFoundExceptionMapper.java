package com.CSA.exception.mappers;

import com.CSA.LoggerUtil.LoggerUtil;
import com.CSA.exception.BookNotFoundException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception mapper for BookNotFoundException.
 * Maps the exception to a 404 Not Found HTTP response.
 */
@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {

    @Override
    public Response toResponse(BookNotFoundException exception) {
        LoggerUtil.logWarning("Book not found exception: " + exception.getMessage());
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "BookNotFoundException");
        errorResponse.put("message", exception.getMessage());
        
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
