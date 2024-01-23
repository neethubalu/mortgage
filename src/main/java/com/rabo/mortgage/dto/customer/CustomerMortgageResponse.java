package com.rabo.mortgage.dto.customer;

import com.rabo.mortgage.dto.Address;
import lombok.Data;

import java.util.UUID;

@Data
public class CustomerMortgageResponse {
    private UUID accountId;
    private String name;
    private Address address;
    private int age;
}
