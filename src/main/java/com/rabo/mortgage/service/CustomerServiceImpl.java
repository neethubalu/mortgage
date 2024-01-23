package com.rabo.mortgage.service;

import com.rabo.mortgage.dto.GenericSuccessResponse;
import com.rabo.mortgage.dto.customer.CreateCustomerRequest;
import com.rabo.mortgage.dto.customer.CustomerMortgageResponse;
import com.rabo.mortgage.dto.customer.CustomerResponse;
import com.rabo.mortgage.dto.customer.UpdateCustomerRequest;
import com.rabo.mortgage.dto.mortgage.DeleteMortgageRequest;
import com.rabo.mortgage.entity.Customer;
import com.rabo.mortgage.exceptions.customer.*;
import com.rabo.mortgage.repository.CustomerRepository;
import com.rabo.mortgage.utils.AgeClient;
import com.rabo.mortgage.utils.ApiConstants;
import com.rabo.mortgage.utils.ValidationWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final AgeClient ageWrapper;
    private final ValidationWrapper validationWrapper;

    @Override
    public GenericSuccessResponse createCustomer(CreateCustomerRequest createCustomerRequest) throws AgeCalculationException, CustomerCreationException {
        var customer = modelMapper.map(createCustomerRequest, Customer.class);
        customer.setAge(ageWrapper.findCustomerAgeByName(customer.getName()));
        try {
            customer = customerRepository.save(customer);
        } catch (Exception e) {
            throw new CustomerCreationException(e);
        }
        CustomerMortgageResponse customerMortgageResponse = modelMapper.map(customer, CustomerMortgageResponse.class);
        log.info("Created customer for: {} with ID: {} ", createCustomerRequest.getName(), customer.getAccountId());
        return validationWrapper.mapSuccessResponse(customerMortgageResponse, ApiConstants.CUSTOMER_CREATION_MSG);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        log.info("List of customers returned");
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerResponse.class))
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(UUID id) throws CustomerNotFoundException {
        var customer = findCustomerById(id);
        log.info("Returned requested customer details of: {} ", id);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    private Customer findCustomerById(UUID id) throws CustomerNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(ApiConstants.CUSTOMER_NOT_FOUND_MSG));
    }

    @Override
    public GenericSuccessResponse deleteMortgageForCustomer(DeleteMortgageRequest deleteMortgageRequest) throws CustomerNotFoundException {
        var customer = findCustomerById(deleteMortgageRequest.getCustomerId());
        customer.getMortgages().removeIf(mortgage -> mortgage.getMortgageId().equals(deleteMortgageRequest.getMortgageId()));
        customerRepository.save(customer);
        log.info("Deleted mortgage {} for customer {}", deleteMortgageRequest.getMortgageId(), deleteMortgageRequest.getCustomerId());
        return validationWrapper.mapSuccessResponse(customer, ApiConstants.MORTGAGE_DELETION_MSG);
    }

    @Override
    public GenericSuccessResponse deleteCustomer(UUID customerId) throws CustomerNotFoundException, CustomerDeletionException {
        var customer = findCustomerById(customerId);
        if (customer.getMortgages().isEmpty()) {
            customerRepository.delete(customer);
            log.info("Deleted customer " + customerId);
            return validationWrapper.mapSuccessResponse(customerId, ApiConstants.DELETED);
        } else {
            log.error("Cannot delete customer {}, there are existing mortgages for the requested customer", customerId);
            throw new CustomerDeletionException(ApiConstants.CUSTOMER_CANNOT_BE_REMOVED);
        }
    }

    @Override
    public GenericSuccessResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException, CustomerUpdationException {
        var customer = findCustomerById(updateCustomerRequest.getAccountId());
        modelMapper.map(updateCustomerRequest, customer);
        try {
            customer = customerRepository.save(customer);
        } catch (Exception e) {
            throw new CustomerUpdationException(e);
        }
        CustomerMortgageResponse customerMortgageResponse = modelMapper.map(customer, CustomerMortgageResponse.class);
        log.info("Updated customer {}", customer.getAccountId());
        return validationWrapper.mapSuccessResponse(customerMortgageResponse, ApiConstants.CUSTOMER_UPDATE_MSG);
    }
}
