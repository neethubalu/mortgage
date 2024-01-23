package com.rabo.mortgage.controller;

import com.rabo.mortgage.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MortgageControllerTest extends IntegrationTest {
    @Test
    void mortgageCreation() throws Exception {
        mvc.perform((post("/rabo/mortgage/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"mortgageSum\": 350000.0, \"startDate\": \"2024-01-20\", \"endDate\": \"2029-01-20\", \"interestPercentage\": 3.5, \"mortgageType\": \"ANN\", \"customers\": [] }")
        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("350000.0")));
    }
    @Test
    void mortgageCreationBlankException() throws Exception {
        mvc.perform((post("/rabo/mortgage/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"mortgageSum\": 350000.0, \"startDate\": \"2024-01-20\", \"endDate\": \"2029-01-20\", \"interestPercentage\": 3.5, \"mortgageType\": \"ANN\", \"customers\": [] }")
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("350000.0")));
    }
    @Test
    void getAllMortgages () throws Exception {
        mvc.perform((get("/rabo/mortgage/get")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("350000.0")));;
    }

    @Test
    void getMortgageByIdSuccess() throws Exception {
        UUID id = mortgageRepository.findAllMortgageIds().getFirst();
        mvc.perform((get("/rabo/mortgage/get/{id}", id)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(mortgageRepository.findById(id).orElseThrow().getMortgageSum()))));
    }
    @Test
    void getMortgageByIdNotFound() throws Exception {
        mvc.perform((get("/rabo/mortgage/get/{id}", "b97b862d-def2-4995-8867-314a267b69d1")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMortgageByIdNotFound() throws Exception {
        mvc.perform((delete("/rabo/mortgage/delete/{id}", "b97b862d-def2-4995-8867-314a267b69d1")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMortgageByIdConflict() throws Exception {
        UUID id = mortgageRepository.findAllMortgageIds().getFirst();
        mvc.perform((delete("/rabo/mortgage/delete/{id}", id)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Mortgage cannot be deleted as customers still exists in this mortgage")));
    }

    @Test
    void updateMortgage() throws Exception {
        mvc.perform((put("/rabo/mortgage/update"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"mortgageId\": \"a14b7a4d-53bf-4b4c-92db-04a922f0c2a7\","
                        + "\"mortgageSum\": 350000.0,"
                        + "\"startDate\": \"2024-01-20\","
                        + "\"endDate\": \"2029-01-20\","
                        + "\"interestPercentage\": 3.5,"
                        + "\"mortgageType\": \"ANN\","
                        + "\"customers\": ["
                        + "\"4eae501d-94fc-42ef-97c5-2a2f1150c6fb\","
                        + "\"a46c12d8-8cf3-4eef-a480-b58d92e9d1cd\""
                        + "]"
                        + "}"))
                .andExpect(status().isNotFound());
    }
    @Test
    void addCustomerToMortgage() throws Exception {
        mvc.perform((put("/rabo/mortgage/addCustomer"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"mortgageId\": \"a14b7a4d-53bf-4b4c-92db-04a922f0c2a7\","
                                + "\"mortgageSum\": 350000.0,"
                                + "\"startDate\": \"2024-01-20\","
                                + "\"endDate\": \"2029-01-20\","
                                + "\"interestPercentage\": 3.5,"
                                + "\"mortgageType\": \"ANN\","
                                + "\"customers\": ["
                                + "\"4eae501d-94fc-42ef-97c5-2a2f1150c6fb\","
                                + "\"a46c12d8-8cf3-4eef-a480-b58d92e9d1cd\""
                                + "]"
                                + "}"))
                .andExpect(status().isNotFound());
    }
}
