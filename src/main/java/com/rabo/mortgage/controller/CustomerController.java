package com.rabo.mortgage.controller;

import com.rabo.mortgage.dto.GenericSuccessResponse;
import com.rabo.mortgage.dto.customer.CreateCustomerRequest;
import com.rabo.mortgage.dto.customer.CustomerResponse;
import com.rabo.mortgage.dto.customer.UpdateCustomerRequest;
import com.rabo.mortgage.dto.mortgage.DeleteMortgageRequest;
import com.rabo.mortgage.exceptions.customer.*;
import com.rabo.mortgage.exceptions.mortgage.MortgageDeletionException;
import com.rabo.mortgage.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Class having customer-centric rest APIs
 */
@RestController
@RequestMapping("rabo/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Handles POST requests to create a new customer
     *
     * @param createCustomerRequest the request body containing information to create customer
     * @return ResponseEntity<GenericSuccessResponse> containing success message and created customer details
     */
    @PostMapping("create")
    ResponseEntity<GenericSuccessResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) throws CustomerCreationException, AgeCalculationException {
        log.info("Received customer creation request for: {}", createCustomerRequest.getName());
        return ResponseEntity.ok().body(customerService.createCustomer(createCustomerRequest));
    }

    /**
     * Handles GET requests to retrieve all customers
     *
     * @return ResponseEntity containing success message and list of customers along with their associated mortgages if any.
     */
    @GetMapping("get")
    ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        log.info("Received request for fetching all customers");
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    /**
     * Handles GET requests to get a customer by account number
     *
     * @param accountNumber the customer account number
     * @return ResponseEntity containing success message and requested customer details if present
     */
    @GetMapping("get/{accountNumber}")
    ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID accountNumber) throws CustomerNotFoundException {
        log.info("Received request for fetching details of customer {}", accountNumber);
        return ResponseEntity.ok().body(customerService.getCustomerById(accountNumber));
    }

    /**
     * Handles DELETE requests to delete a mortgage for a customer
     *
     * @param deleteMortgageRequest the request body with mortgage id and customer account number
     * @return ResponseEntity containing success message and requested customer details if present
     */
    @DeleteMapping("delete")
    ResponseEntity<GenericSuccessResponse> deleteMortgageForCustomer(@RequestBody DeleteMortgageRequest deleteMortgageRequest) throws CustomerNotFoundException {
        log.info("Received request for fetching all customers");
        return ResponseEntity.ok().body(customerService.deleteMortgageForCustomer(deleteMortgageRequest));
    }

    /**
     * Handles DELETE requests to delete a customer
     * Deletes customer only if there is no mortgage present for that customer
     *
     * @param accountNumber the customer account number
     * @return ResponseEntity containing success message and deleted customer account number
     */
    @DeleteMapping("delete/{accountNumber}")
    ResponseEntity<GenericSuccessResponse> deleteCustomer(@PathVariable UUID accountNumber) throws MortgageDeletionException, CustomerNotFoundException, CustomerDeletionException {
        log.info("Received request for deleting customer {}", accountNumber);
        return ResponseEntity.ok().body(customerService.deleteCustomer(accountNumber));
    }

    /**
     * Handles UPDATE requests to update a customer
     *
     * @param updateCustomerRequest the request body with customer account number and address
     *                              Only address is allowed to edit
     * @return ResponseEntity containing success message and updated customer details
     */
    @PutMapping("update")
    ResponseEntity<GenericSuccessResponse> updateMortgage(@RequestBody UpdateCustomerRequest updateCustomerRequest) throws CustomerUpdationException, CustomerNotFoundException {
        log.info("Request received for updating customer {}", updateCustomerRequest.getAccountId());
        return ResponseEntity.ok().body(customerService.updateCustomer(updateCustomerRequest));
    }
}
