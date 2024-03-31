package com.springframework.globaltimes.dto.geography;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateCityRequest(
        UUID timezoneId,

        String cityName,

        Boolean highlighted,

        @NotNull
        UUID lastOpId
) {
}
