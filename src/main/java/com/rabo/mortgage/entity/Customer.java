package com.rabo.mortgage.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rabo.mortgage.dto.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;
    private String name;
    @JdbcTypeCode(SqlTypes.JSON)
    private Address address;
    private Integer age;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_mortgage",
            joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "mortgageId", referencedColumnName = "mortgageId"))
    private List<Mortgage> mortgages;
}
