package com.springframework.globaltimes.config;

import com.springframework.globaltimes.auth.ApiKeyAuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.regex.Pattern;

@Slf4j
@Configuration
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
            "/actuator/**",

            // OpenAPI 3.0 Specification
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private static final String[] BLACK_LIST = {
            "/actuator/restart"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var username = "X-API-KEY";
        var password = "secret";

        var filter = new ApiKeyAuthFilter(username);
        filter.setAuthenticationManager(authentication -> {
            var principal = (String) authentication.getPrincipal();
            if (!password.equals(principal)){
                throw new BadCredentialsException("Invalid API Key");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });

        http.csrf(csrf -> csrf.requireCsrfProtectionMatcher(new RequestMatcher() {
            private final Pattern allowedMethods = Pattern.compile("^(GET|POST|PUT|DELETE|PATCH)$");

            @Override
            public boolean matches(HttpServletRequest request) {
                return !allowedMethods.matcher(request.getMethod()).matches();
            }
        }));

        http.securityMatcher("/**")
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(filter)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(BLACK_LIST).denyAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
