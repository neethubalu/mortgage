package com.rabo.mortgage;

import com.rabo.mortgage.repository.CustomerRepository;
import com.rabo.mortgage.repository.MortgageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
abstract public class IntegrationTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected MortgageRepository mortgageRepository;

}