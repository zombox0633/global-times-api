package com.springframework.globaltimes.dto.user;

import com.springframework.globaltimes.constants.Index;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserChangePasswordRequest(

        @NotNull
        String oldPassword,

        @NotNull
        @Pattern(regexp = Index.PASSWORD_REGEX)
        String newPassword,

        @NotNull
        @Pattern(regexp = Index.PASSWORD_REGEX)
        String confirmNewPassword
) {
}
