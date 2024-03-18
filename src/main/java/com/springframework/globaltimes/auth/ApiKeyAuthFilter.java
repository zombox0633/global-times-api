package com.springframework.globaltimes.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@RequiredArgsConstructor
public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final String principalRequestHeader;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request){
        return request.getHeader(principalRequestHeader);
    }

    @Override
    protected  Object getPreAuthenticatedCredentials(HttpServletRequest request){
        return "N/A";
    }
}
