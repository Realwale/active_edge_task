package com.exercises.activeedgetask.exercise3.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleResourceNotFoundException(Exception e) {
        return buildErrorResponse(new APIErrorResponse(HttpStatus.NOT_FOUND,
                e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<APIErrorResponse> handleInvalidArgumentException(Exception e){
        return buildErrorResponse(new APIErrorResponse(HttpStatus.BAD_REQUEST,
                e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<APIErrorResponse> handleResourceAlreadyExistsException(Exception e){
        return buildErrorResponse(new APIErrorResponse(HttpStatus.ALREADY_REPORTED,
                e.getMessage(), HttpStatus.ALREADY_REPORTED.value()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<APIErrorResponse> handleValidationException(Exception e){
        return buildErrorResponse(new APIErrorResponse(HttpStatus.BAD_REQUEST,
                e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }


    private ResponseEntity<APIErrorResponse> buildErrorResponse(APIErrorResponse apiErrorResponse) {
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());

    }
}