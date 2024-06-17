package com.exercises.activeedgetask.exercise3.exception;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return buildErrorResponse(new ExceptionHandlerResponse(HttpStatus.NOT_FOUND,
                e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e){
        return buildErrorResponse(new ExceptionHandlerResponse(HttpStatus.ALREADY_REPORTED,
                e.getMessage(), HttpStatus.ALREADY_REPORTED.value()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleValidationException(ValidationException e){
        return buildErrorResponse(new ExceptionHandlerResponse(HttpStatus.BAD_REQUEST,
                e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    private ResponseEntity<ExceptionHandlerResponse> buildErrorResponse(ExceptionHandlerResponse exceptionHandlerResponse) {
        return new ResponseEntity<>(exceptionHandlerResponse, exceptionHandlerResponse.getStatus());

    }
}