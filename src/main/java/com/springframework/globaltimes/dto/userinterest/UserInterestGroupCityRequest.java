package com.springframework.globaltimes.dto.userinterest;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserInterestGroupCityRequest(
        @NotNull
        UUID groupId,

        @NotNull
        UUID cityId,

        @NotNull
        UUID lastOpId
) {
}
