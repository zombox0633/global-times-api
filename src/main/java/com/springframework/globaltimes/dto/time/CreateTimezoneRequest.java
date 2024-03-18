package com.springframework.globaltimes.dto.time;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTimezoneRequest(

        @NotNull
        UUID countryId,

        @NotNull
        @Pattern(regexp = "^[A-Za-z]+(/[_-]?[A-Za-z]+)*(?:/[A-Za-z]+(?:[_-]?[A-Za-z]+)*)+$", message = "Invalid timezone name format.")
        String timezoneName,

        @NotNull
        UUID lastOpId
) {
}
