package com.rabo.mortgage.dto.mortgage;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DeleteMortgageRequest {
    @NotNull
    private UUID customerId;
    @NotNull
    private UUID mortgageId;
}
