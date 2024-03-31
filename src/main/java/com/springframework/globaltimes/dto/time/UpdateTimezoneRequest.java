package com.springframework.globaltimes.dto.time;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdateTimezoneRequest(
        UUID countryId,

        @NotNull
        @Pattern(regexp = "^[A-Za-z]+(/[_-]?[A-Za-z]+)*(?:/[A-Za-z]+(?:[_-]?[A-Za-z]+)*)+$", message = "Invalid timezone name format.")
        String timezoneName,

        @NotNull
        UUID lastOpId
) {
}
