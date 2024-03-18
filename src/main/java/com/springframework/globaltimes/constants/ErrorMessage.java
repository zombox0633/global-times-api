package com.springframework.globaltimes.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {
    public static final String NOT_FOUND = "%s not found";

    public static final String INVALID_REQUEST_LOG = "Invalid request: %s";
    public static final String EXCEPTION_REQUEST_LOG = "Exception request: %s";
    public static final String FAILED_LOG = "Failed to %s";

    public static final String INVALID_SORT_BY = "unable to sort by column %s";
}
