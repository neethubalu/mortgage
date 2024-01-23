package com.rabo.mortgage.utils;

public class ApiConstants {
    private ApiConstants() {
    }

    public static final String CUSTOMER_CREATION_MSG = "Customer created successfully";
    public static final String MORTGAGE_CREATION_MSG = "Mortgage created successfully";
    public static final String CUSTOMER_UPDATE_MSG = "Customer updated successfully";
    public static final String MORTGAGE_UPDATE_MSG = "Mortgage updated successfully";
    public static final String CUSTOMER_TO_MORTGAGE_MSG = "Mortgage updated successfully";

    public static final String CUSTOMER_NOT_FOUND_MSG = "Customer doesn't exist";
    public static final String MORTGAGE_NOT_FOUND = "Mortgage doesn't exist";
    public static final String DELETED = "Successfully Deleted";
    public static final String MORTGAGE_DELETION_MSG = "Mortgage successfully deleted";

    public static final String MORTGAGE_CANNOT_BE_REMOVED = "Mortgage cannot be deleted as customers still exists in this mortgage";
    public static final String CUSTOMER_CANNOT_BE_REMOVED = "Customer cannot be removed as mortgages still exists for the customer";


}
