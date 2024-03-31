package com.springframework.globaltimes.dto.userinterest;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserInterestRequest(

        String name,

        String description,

        @NotNull
        UUID lastOpId
) {
}
