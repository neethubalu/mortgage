package com.rabo.mortgage.service;

import com.rabo.mortgage.dto.GenericSuccessResponse;
import com.rabo.mortgage.dto.mortgage.CreateMortgageRequest;
import com.rabo.mortgage.dto.mortgage.MortgageResponse;
import com.rabo.mortgage.dto.mortgage.UpdateMortgageRequest;
import com.rabo.mortgage.exceptions.customer.CustomerNotFoundException;
import com.rabo.mortgage.exceptions.mortgage.MortgageCreationException;
import com.rabo.mortgage.exceptions.mortgage.MortgageDeletionException;
import com.rabo.mortgage.exceptions.mortgage.MortgageNotFoundException;
import com.rabo.mortgage.exceptions.mortgage.MortgageUpdationException;

import java.util.List;
import java.util.UUID;

public interface MortgageService {
    GenericSuccessResponse createMortgage(CreateMortgageRequest createMortgageRequest) throws MortgageCreationException;

    List<MortgageResponse> getAllMortgages();

    MortgageResponse getMortgageById(UUID id) throws CustomerNotFoundException, MortgageNotFoundException;

    GenericSuccessResponse deleteMortgage(UUID mortgageId) throws CustomerNotFoundException, MortgageDeletionException, MortgageNotFoundException;

    GenericSuccessResponse updateMortgage(UpdateMortgageRequest updateMortgageRequest) throws CustomerNotFoundException, MortgageUpdationException, MortgageNotFoundException;

    GenericSuccessResponse addCustomerToMortgage(UpdateMortgageRequest updateMortgageRequest) throws MortgageNotFoundException;
}
