package com.springframework.globaltimes.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Health Check", description = "Check the health status of the application")
    public String healthCheck() {
        return "OK";
    }

}
