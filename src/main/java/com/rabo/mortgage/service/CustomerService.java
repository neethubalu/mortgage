package com.rabo.mortgage.service;

import com.rabo.mortgage.dto.GenericSuccessResponse;
import com.rabo.mortgage.dto.customer.CreateCustomerRequest;
import com.rabo.mortgage.dto.customer.CustomerResponse;
import com.rabo.mortgage.dto.customer.UpdateCustomerRequest;
import com.rabo.mortgage.dto.mortgage.DeleteMortgageRequest;
import com.rabo.mortgage.exceptions.customer.*;
import com.rabo.mortgage.exceptions.mortgage.MortgageDeletionException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    GenericSuccessResponse createCustomer(CreateCustomerRequest createCustomerRequest) throws CustomerCreationException, AgeCalculationException;

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(UUID id) throws CustomerNotFoundException;

    GenericSuccessResponse deleteMortgageForCustomer(DeleteMortgageRequest deleteMortgageRequest) throws CustomerNotFoundException;

    GenericSuccessResponse deleteCustomer(UUID customerId) throws MortgageDeletionException, CustomerNotFoundException, CustomerDeletionException;

    GenericSuccessResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException, CustomerUpdationException;

}
