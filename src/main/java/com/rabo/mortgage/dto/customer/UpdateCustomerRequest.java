package com.rabo.mortgage.dto.customer;

import com.rabo.mortgage.dto.Address;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateCustomerRequest {
    @NotNull
    private UUID accountId;
    @NotNull
    private Address address;

}
