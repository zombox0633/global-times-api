package com.springframework.globaltimes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    // http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI(){
        var securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-API-KEY");

        var components = new Components();
        components.addSecuritySchemes("Authentication-Key", securityScheme);

        var securityRequirement = new SecurityRequirement().addList("Authentication-Key");
        var security = Collections.singletonList(securityRequirement);

        var info = new Info()
                .title("Spring Fundamental")
                .description("Spring Fundamental2")
                .version("1.0.0");

        return new OpenAPI()
                .components(components)
                .security(security)
                .info(info);
    }
}