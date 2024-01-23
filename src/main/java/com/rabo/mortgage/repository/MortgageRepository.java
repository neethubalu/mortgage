package com.rabo.mortgage.repository;

import com.rabo.mortgage.entity.Mortgage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MortgageRepository extends JpaRepository<Mortgage, UUID> {
    @Query("SELECT M.mortgageId FROM Mortgage M")
    List<UUID> findAllMortgageIds();
}
