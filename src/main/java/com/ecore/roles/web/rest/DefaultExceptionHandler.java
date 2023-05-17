package com.ecore.roles.web.rest;

import com.ecore.roles.exception.ErrorResponse;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.exception.UserNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException exception) {
        return createResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(ResourceExistsException exception) {
        return createResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(IllegalStateException exception) {
        return createResponse(500, exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(UserNotAllowedException exception) {
        return createResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }


    private ResponseEntity<ErrorResponse> createResponse(int status, String exception) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .status(status)
                        .error(exception).build());
    }
}
