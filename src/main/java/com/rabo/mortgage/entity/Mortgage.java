package com.rabo.mortgage.entity;

import com.rabo.mortgage.enums.MortgageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Mortgage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID mortgageId;
    private Double mortgageSum;
    private String startDate;
    private String endDate;
    private Double interestPercentage;
    private MortgageType mortgageType;

    @ManyToMany
//            (mappedBy = "mortgages" )
    @JoinTable(name = "customer_mortgage",
            joinColumns = @JoinColumn(name = "mortgageId", referencedColumnName = "mortgageId"),
            inverseJoinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"))
    private List<Customer> customers;

}
