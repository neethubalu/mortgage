package com.rabo.mortgage.dto.mortgage;

import com.rabo.mortgage.enums.MortgageType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateMortgageRequest {
    @NotNull
    private UUID mortgageId;
    private Double mortgageSum;
    private Date startDate;
    private Date endDate;
    private Double interestPercentage;
    private MortgageType mortgageType;
    private List<UUID> customers;
}
