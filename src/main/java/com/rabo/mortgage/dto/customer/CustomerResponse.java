package com.rabo.mortgage.dto.customer;

import com.rabo.mortgage.dto.Address;
import com.rabo.mortgage.dto.mortgage.MortgageCustomerResponse;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CustomerResponse {

    private UUID accountId;
    private String name;
    private Address address;
    private int age;
    private List<MortgageCustomerResponse> mortgages;
}
