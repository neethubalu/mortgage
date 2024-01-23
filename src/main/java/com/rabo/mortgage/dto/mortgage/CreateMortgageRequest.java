package com.rabo.mortgage.dto.mortgage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rabo.mortgage.enums.MortgageType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CreateMortgageRequest {

    @NotNull(message = "Mortgage SUm must not be blank")
    private Double mortgageSum;

    @NotNull(message = "Start date must not be blank")
    @JsonFormat(pattern = "YYYY-MM-DD")
    @PastOrPresent(message = "Invalid Date")
    private Date startDate;

    @NotNull(message = "End date must not be blank")
    @JsonFormat(pattern = "YYYY-MM-DD")
    @Future(message = "Invalid Date")
    private Date endDate;

    @NotNull(message = "Interest Percentage must not be blank")
    private Double interestPercentage;

    @NotNull(message = "MortgageType must not be blank")
    private MortgageType mortgageType;
    private List<UUID> customers;

}
