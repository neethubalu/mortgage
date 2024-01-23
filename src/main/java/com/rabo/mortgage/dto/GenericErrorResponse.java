package com.rabo.mortgage.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class GenericErrorResponse {
    private HttpStatus errorCode;
    private String errorMessage;
}
