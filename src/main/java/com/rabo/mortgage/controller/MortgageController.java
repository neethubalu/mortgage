package com.rabo.mortgage.controller;

import com.rabo.mortgage.dto.GenericSuccessResponse;
import com.rabo.mortgage.dto.mortgage.CreateMortgageRequest;
import com.rabo.mortgage.dto.mortgage.MortgageResponse;
import com.rabo.mortgage.dto.mortgage.UpdateMortgageRequest;
import com.rabo.mortgage.exceptions.customer.CustomerNotFoundException;
import com.rabo.mortgage.exceptions.mortgage.MortgageCreationException;
import com.rabo.mortgage.exceptions.mortgage.MortgageDeletionException;
import com.rabo.mortgage.exceptions.mortgage.MortgageNotFoundException;
import com.rabo.mortgage.exceptions.mortgage.MortgageUpdationException;
import com.rabo.mortgage.service.MortgageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Class having mortgage-centric rest APIs
 */
@RestController
@RequestMapping("/rabo/mortgage")
@RequiredArgsConstructor
@Slf4j
public class MortgageController {

    private final MortgageService mortgageService;

    /**
     * Handles POST requests to create a new mortgage
     *
     * @param createMortgageRequest the request body containing information to create mortgage
     * @return ResponseEntity<GenericSuccessResponse> containing success message and created mortgage details
     */
    @PostMapping("create")
    ResponseEntity<GenericSuccessResponse> mortgageCreation(@Valid @RequestBody CreateMortgageRequest createMortgageRequest) throws MortgageCreationException {
        log.info("Received mortgage creation request for: {}", createMortgageRequest);
        return ResponseEntity.ok().body(mortgageService.createMortgage(createMortgageRequest));
    }

    /**
     * Handles GET requests to retrieve all mortgages
     *
     * @return ResponseEntity containing success message and list of mortgages along with their associated customer details if any.
     */
    @GetMapping("get")
    ResponseEntity<List<MortgageResponse>> getAllMortgages() {
        log.info("Received request for fetching all mortgages");
        return ResponseEntity.ok().body(mortgageService.getAllMortgages());
    }

    /**
     * Handles GET requests to get a mortgage by mortgage id
     *
     * @param mortgageId the mortgage id
     * @return ResponseEntity containing success message and requested mortgage details if present
     */
    @GetMapping("get/{mortgageId}")
    ResponseEntity<MortgageResponse> getMortgageById(@PathVariable UUID mortgageId) throws CustomerNotFoundException, MortgageNotFoundException {
        log.info("Received request for fetching details of mortgage {}", mortgageId);
        return ResponseEntity.ok().body(mortgageService.getMortgageById(mortgageId));
    }

    /**
     * Handles DELETE requests to delete a mortgage
     * Deletes mortgage only if there is no customer associated with that mortgage
     *
     * @param mortgageId the mortgage id
     * @return ResponseEntity containing success message and deleted mortgage details
     */
    @DeleteMapping("delete/{mortgageId}")
    ResponseEntity<GenericSuccessResponse> deleteMortgage(@PathVariable UUID mortgageId) throws CustomerNotFoundException, MortgageDeletionException, MortgageNotFoundException {
        log.info("Received request for deleting mortgage {} ", mortgageId);
        return ResponseEntity.ok().body(mortgageService.deleteMortgage(mortgageId));
    }

    /**
     * Handles UPDATE requests to update a mortgage
     *
     * @param updateMortgageRequest the request body with mortgage details
     * @return ResponseEntity containing success message and updated mortgage details
     */
    @PutMapping("update")
    ResponseEntity<GenericSuccessResponse> updateMortgage(@RequestBody UpdateMortgageRequest updateMortgageRequest) throws CustomerNotFoundException, MortgageUpdationException, MortgageNotFoundException {
        log.info("Request received for updating customer {}", updateMortgageRequest.getMortgageId());
        return ResponseEntity.ok().body(mortgageService.updateMortgage(updateMortgageRequest));
    }

    /**
     * Handles UPDATE requests to add customers to a mortgage
     *
     * @param updateMortgageRequest the request body with mortgage id and customer account number
     * @return ResponseEntity containing success message and updated mortgage details
     */
    @PutMapping("addCustomer")
    ResponseEntity<GenericSuccessResponse> addCustomerToMortgage(@RequestBody UpdateMortgageRequest updateMortgageRequest) throws MortgageNotFoundException {
        log.info("Received request for adding customers to mortgage {}", updateMortgageRequest.getMortgageId());
        return ResponseEntity.ok().body(mortgageService.addCustomerToMortgage(updateMortgageRequest));
    }

}
