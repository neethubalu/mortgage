package com.rabo.mortgage.exceptions.customer;

public class CustomerCreationException extends Exception {
    public CustomerCreationException(Exception message) {
        super(message);
    }
}
