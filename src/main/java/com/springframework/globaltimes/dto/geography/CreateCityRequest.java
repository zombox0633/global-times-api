package com.springframework.globaltimes.dto.geography;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCityRequest(

        @NotNull
        UUID timezoneId,

        @NotNull
        String cityName,

        Boolean highlighted,

        @NotNull
        UUID lastOpId
) {
}
