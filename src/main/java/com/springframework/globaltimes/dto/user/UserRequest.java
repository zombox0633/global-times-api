package com.springframework.globaltimes.dto.user;

import com.springframework.globaltimes.constants.Index;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
        @NotNull
        @Email
        String email,

        @NotNull
        @Pattern(regexp = Index.PASSWORD_REGEX)
        String password
){}
