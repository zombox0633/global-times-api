package com.springframework.globaltimes.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UseRegisterRequest(

        @NotNull
        @Email
        String email,

        @NotNull
        String jwt
) {
}
