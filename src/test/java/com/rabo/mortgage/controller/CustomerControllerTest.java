package com.rabo.mortgage.controller;

import com.rabo.mortgage.IntegrationTest;
import com.rabo.mortgage.utils.DataLoader;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class CustomerControllerTest extends IntegrationTest {

    @Autowired
    DataLoader dataLoader;

    @BeforeEach
    void load() {
        dataLoader.loadCustomersOnStartup();
    }

    @Test
    void customerCreationSuccess() throws Exception {
        mvc.perform((post("/rabo/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Hubrecht Steve\", \"address\": { \"street\": \"83 East Spruce Street\", \"state\": \"Stroudsburg\", \"postalCode\": \"PA 18360\" }}")
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hubrecht")));
    }

    @Test
    void customerCreationAgeException() throws Exception {
        mvc.perform((post("/rabo/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"dfbdfbhdfgbsfgsf\", \"address\": { \"street\": \"83 East Spruce Street\", \"state\": \"Stroudsburg\", \"postalCode\": \"PA 18360\" }}")
                ))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Couldn't calculate the Age , please try again later")));
    }

    @Test
    void customerCreationBlankNameException() throws Exception {
        mvc.perform((post("/rabo/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\": { \"street\": \"83 East Spruce Street\", \"state\": \"Stroudsburg\", \"postalCode\": \"PA 18360\" }}")
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Name must not be blank")));
    }

    @Test
    void getAllCustomers() throws Exception {
        mvc.perform((get("/rabo/customer/get")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hubrecht")));
    }

    @Test
    void getCustomerByIdSuccess() throws Exception {
        UUID id = customerRepository.findAllAccountIds().getFirst();
        mvc.perform((get("/rabo/customer/get/{accountNumber}", id)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(customerRepository.findById(id).orElseThrow().getName())))
        ;
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {
        mvc.perform((get("/rabo/customer/get/{accountNumber}", "b97b862d-def2-4995-8867-314a267b69d1")))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCustomer() throws Exception {
        mvc.perform((put("/rabo/customer/update"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"accountId\": \"1be5d6b7-e8c8-4d82-abc3-5b31c45f3467\",\n" +
                                "    \"address\": {\n" +
                                "        \"street\": \"Parklaan 6 E.\",\n" +
                                "        \"state\": \"Amsterdam\",\n" +
                                "        \"postalCode\": \"2132 BN\"\n" +
                                "    }\n" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCustomerByIdNOtFound() throws Exception {
        mvc.perform((delete("/rabo/customer/delete/{id}", "1be5d6b7-e8c8-4d82-abc3-5b31c45f3467")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMortgageForCustomer() throws Exception {
        mvc.perform((delete("/rabo/customer/delete"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":\"872d4fe2-6e56-4c26-aa96-086c9b910487\",\"mortgageId\":\"0405bd3a-66e4-43cc-a574-842fccd81bba\"}"))
                .andExpect(status().isNotFound());
    }

}
