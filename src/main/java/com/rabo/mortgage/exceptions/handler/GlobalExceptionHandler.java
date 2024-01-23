package com.rabo.mortgage.exceptions.handler;

import com.rabo.mortgage.dto.GenericErrorResponse;
import com.rabo.mortgage.exceptions.customer.CustomerCreationException;
import com.rabo.mortgage.exceptions.customer.CustomerDeletionException;
import com.rabo.mortgage.exceptions.customer.CustomerNotFoundException;
import com.rabo.mortgage.exceptions.mortgage.MortgageCreationException;
import com.rabo.mortgage.exceptions.mortgage.MortgageDeletionException;
import com.rabo.mortgage.exceptions.mortgage.MortgageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final GenericErrorResponse errorResponse;


    @ExceptionHandler({CustomerNotFoundException.class, MortgageNotFoundException.class})
    public ResponseEntity<GenericErrorResponse> handleCustomerException(Exception e) {
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND);
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MortgageDeletionException.class, CustomerDeletionException.class})
    public ResponseEntity<GenericErrorResponse> handleDeletionException(Exception e) {
        errorResponse.setErrorCode(HttpStatus.CONFLICT);
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericErrorResponse> handleException(Exception e) {
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({MortgageCreationException.class, CustomerCreationException.class})
    public ResponseEntity<GenericErrorResponse> handleCreationException(Exception e) {
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<GenericErrorResponse> handleBadRequestException(MethodArgumentNotValidException e) {
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
