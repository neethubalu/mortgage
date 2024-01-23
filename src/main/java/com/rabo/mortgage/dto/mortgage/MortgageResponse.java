package com.rabo.mortgage.dto.mortgage;

import com.rabo.mortgage.dto.customer.CustomerMortgageResponse;
import com.rabo.mortgage.enums.MortgageType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MortgageResponse {
    private UUID mortgageId;
    private Double mortgageSum;
    private Date startDate;
    private Date endDate;
    private Double interestPercentage;
    private MortgageType mortgageType;
    private List<CustomerMortgageResponse> customers;
}
