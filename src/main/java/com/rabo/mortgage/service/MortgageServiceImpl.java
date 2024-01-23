package com.rabo.mortgage.service;

import com.rabo.mortgage.dto.GenericSuccessResponse;
import com.rabo.mortgage.dto.mortgage.CreateMortgageRequest;
import com.rabo.mortgage.dto.mortgage.MortgageResponse;
import com.rabo.mortgage.dto.mortgage.UpdateMortgageRequest;
import com.rabo.mortgage.entity.Customer;
import com.rabo.mortgage.entity.Mortgage;
import com.rabo.mortgage.exceptions.customer.CustomerNotFoundException;
import com.rabo.mortgage.exceptions.mortgage.MortgageCreationException;
import com.rabo.mortgage.exceptions.mortgage.MortgageDeletionException;
import com.rabo.mortgage.exceptions.mortgage.MortgageNotFoundException;
import com.rabo.mortgage.exceptions.mortgage.MortgageUpdationException;
import com.rabo.mortgage.repository.CustomerRepository;
import com.rabo.mortgage.repository.MortgageRepository;
import com.rabo.mortgage.utils.ApiConstants;
import com.rabo.mortgage.utils.ValidationWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class MortgageServiceImpl implements MortgageService {
    private final ModelMapper modelMapper;
    private final MortgageRepository mortgageRepository;
    private final CustomerRepository customerRepository;
    private final ValidationWrapper validationWrapper;

    @Override
    public GenericSuccessResponse createMortgage(CreateMortgageRequest createMortgageRequest) throws MortgageCreationException {
        try {
            var mortgageEntity = modelMapper.map(createMortgageRequest, Mortgage.class);
            if (!createMortgageRequest.getCustomers().isEmpty()) {
                List<Customer> customerEntities = customerRepository.findAllById(createMortgageRequest.getCustomers());
                if (customerEntities.isEmpty())
                    throw new CustomerNotFoundException(ApiConstants.CUSTOMER_NOT_FOUND_MSG);
                mortgageEntity.setCustomers(customerEntities);
            }
            mortgageEntity = mortgageRepository.save(mortgageEntity);

            log.info("Created mortgage with ID: {} ", mortgageEntity.getMortgageId());
            return validationWrapper.mapSuccessResponse(modelMapper.map(mortgageEntity, MortgageResponse.class), ApiConstants.MORTGAGE_CREATION_MSG);

        } catch (Exception e) {
            throw new MortgageCreationException(e.getMessage());
        }
    }

    @Override
    public List<MortgageResponse> getAllMortgages() {
        log.info("List of mortgages returned");
        return mortgageRepository.findAll().stream().map(mortgage -> modelMapper.map(mortgage, MortgageResponse.class)).toList();
    }

    @Override
    public MortgageResponse getMortgageById(UUID id) throws MortgageNotFoundException {
        log.info("Returned requested mortgage details of: {} ", id);
        return modelMapper.map(findMortgageById(id), MortgageResponse.class);
    }

    @Override
    public GenericSuccessResponse deleteMortgage(UUID mortgageId) throws MortgageDeletionException, MortgageNotFoundException {
        var mortgage = findMortgageById(mortgageId);
        if (mortgage.getCustomers().isEmpty()) {
            mortgageRepository.delete(mortgage);
            log.info("Deleted mortgage " + mortgageId);
            return validationWrapper.mapSuccessResponse(mortgage, ApiConstants.DELETED);
        } else {
            log.error("Cannot delete mortgage {}, there are existing mortgages for the requested customer", mortgageId);
            throw new MortgageDeletionException(ApiConstants.MORTGAGE_CANNOT_BE_REMOVED);
        }
    }

    @Override
    public GenericSuccessResponse updateMortgage(UpdateMortgageRequest updateMortgageRequest) throws MortgageUpdationException, MortgageNotFoundException {
        var mortgage = findMortgageById(updateMortgageRequest.getMortgageId());
        modelMapper.map(updateMortgageRequest, mortgage);
        try {
            mortgage = mortgageRepository.save(mortgage);
        } catch (Exception e) {
            throw new MortgageUpdationException(e);
        }
        log.info("Updated mortgage {}", mortgage.getMortgageId());
        return validationWrapper.mapSuccessResponse(modelMapper.map(mortgage, MortgageResponse.class), ApiConstants.MORTGAGE_UPDATE_MSG);
    }

    @Override
    public GenericSuccessResponse addCustomerToMortgage(UpdateMortgageRequest updateMortgageRequest) throws MortgageNotFoundException {
        var mortgage = findMortgageById(updateMortgageRequest.getMortgageId());
        mortgage.getCustomers().addAll(customerRepository.findAllById(updateMortgageRequest.getCustomers()));
        mortgage = mortgageRepository.save(mortgage);
        log.info("Updated mortgage {} with customers", mortgage.getMortgageId());
        return validationWrapper.mapSuccessResponse(modelMapper.map(mortgage, MortgageResponse.class), ApiConstants.CUSTOMER_TO_MORTGAGE_MSG);
    }

    private Mortgage findMortgageById(UUID mortgageId) throws MortgageNotFoundException {
        return mortgageRepository.findById(mortgageId).orElseThrow(() -> new MortgageNotFoundException(ApiConstants.MORTGAGE_NOT_FOUND));
    }

}
