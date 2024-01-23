package com.rabo.mortgage.dto.customer;


import com.rabo.mortgage.dto.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCustomerRequest {

    @Size(min = 3, max = 200, message = "Length of the name should be 3-200 characters")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Address must not be blank")
    private Address address;
}
