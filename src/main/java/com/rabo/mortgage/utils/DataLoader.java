package com.rabo.mortgage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.mortgage.dto.customer.CreateCustomerRequest;
import com.rabo.mortgage.dto.mortgage.CreateMortgageRequest;
import com.rabo.mortgage.repository.CustomerRepository;
import com.rabo.mortgage.service.CustomerService;
import com.rabo.mortgage.service.MortgageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataLoader {

    private final CustomerService customerService;
    private final MortgageService mortgageService;
    private final CustomerRepository customerRepository;

    @PostConstruct
    public void loadCustomersOnStartup() {
        loadCustomers();
    }

    void loadCustomers() {
        List<CreateCustomerRequest> customerRequestList;
        List<UUID> generatedUUIDList;
        ObjectMapper objectMapper = new ObjectMapper();
        CreateMortgageRequest mortgageRequest;
        try {

            File customerLoaderFile = new File("src/main/resources/dataload/CreateCustomers.json");
            File mortgageLoaderFile = new File("src/main/resources/dataload/CreateMortgage.json");
            customerRequestList = objectMapper.readValue(customerLoaderFile, new TypeReference<>() {
            });
            for (CreateCustomerRequest createCustomerRequest : customerRequestList) {
                customerService.createCustomer(createCustomerRequest);
            }
            log.info("Customers Created");
            generatedUUIDList = customerRepository.findAllAccountIds();
            log.info("Ids:" + generatedUUIDList);
            mortgageRequest = objectMapper.readValue(mortgageLoaderFile, new TypeReference<>() {
            });

            mortgageRequest.setCustomers(generatedUUIDList);
            mortgageService.createMortgage(mortgageRequest);
            log.info("Mortgage Associated with Customers");
            log.info("Test Data Loaded Successfully");
        } catch (Exception e) {
            log.warn("Data Loading Failed. Please add customers manually. Exception is :" + e.getMessage());
        }

    }
}
