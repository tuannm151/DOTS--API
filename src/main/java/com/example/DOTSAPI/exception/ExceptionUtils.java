package com.example.DOTSAPI.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ExceptionUtils {
    public static ResponseEntity<Object> raiseException(HttpServletRequest request, HttpServletResponse response, String message,
                                                        List<String> details, HttpStatus status) throws IOException {
        ErrorResponse error = new ErrorResponse(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        error.setMessage(message);
        error.setPath(request.getRequestURI());
        error.setDetail(details);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
        return ResponseEntity.status(status).build();
    }
}
