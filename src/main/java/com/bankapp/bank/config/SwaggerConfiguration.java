package com.bankapp.bank.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Bank API")
                        .description("Bank assignment")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://bank.nl")))
                .externalDocs(new ExternalDocumentation()
                        .description("Bank Api Wiki Documentation")
                        .url("https://www.bank.nl"));
    }
}