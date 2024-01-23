package com.rabo.mortgage.dto.mortgage;

import com.rabo.mortgage.enums.MortgageType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class MortgageCustomerResponse {
    private UUID id;
    private Double mortgageSum;
    private Date startDate;
    private Date endDate;
    private Double interestPercentage;
    private MortgageType mortgageType;
}
