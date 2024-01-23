package com.rabo.mortgage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Address {
    @NotBlank(message = "Street must not be blank")
    @Size(min = 3, max = 200, message = "Length of the Street should be 3-200 characters")
    private String street;

    @NotBlank(message = "State must not be blank")
    @Size(min = 2, max = 200, message = "Length of the state should be 2-200 characters")
    private String state;

    @NotBlank(message = "Postal code must not be blank")
    @Size(min = 3, max = 200, message = "Length of the postal code should be 3-200 characters")
    private String postalCode;
}
