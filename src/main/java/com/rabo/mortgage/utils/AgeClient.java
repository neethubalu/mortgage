package com.rabo.mortgage.utils;

import com.rabo.mortgage.exceptions.customer.AgeCalculationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@Slf4j
public class AgeClient {
    public int findCustomerAgeByName(String name) throws AgeCalculationException {
        try {
            log.info("Connecting to https://api.agify.io/");
            WebClient webClient = WebClient.create("https://api.agify.io/");
            String uri = UriComponentsBuilder.fromPath("/")
                    .queryParam("name", name.split(" ")[0])
                    .build()
                    .toUriString();
            Map response = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            if(response!=null && response.get("age")!=null) {
                log.info("Received successfull response");
                return (int) response.get("age");
            }
            else {
                log.info("Agify.io couldn't calculate the Age");
                throw new AgeCalculationException("Couldn't calculate the Age , please try again later");
            }
        } catch (Exception e) {
            log.info("Couldn't connect with Agify.io");
            throw new AgeCalculationException(e.getMessage());
        }
    }
}
