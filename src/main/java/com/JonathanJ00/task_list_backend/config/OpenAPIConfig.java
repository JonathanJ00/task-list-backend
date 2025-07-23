package com.JonathanJ00.task_list_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class defining the config and a bean to allow for the auto generation of an open API spec.
 */
@Configuration
public class OpenAPIConfig {
    @Bean
    public SwaggerUiConfigParameters swaggerUiConfigParameters() {
        SwaggerUiConfigParameters config = new SwaggerUiConfigParameters(new SwaggerUiConfigProperties());
        config.setUrl("/v3/api-docs.yaml");
        return config;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task List API Documentation")
                        .version("1.0")
                        .description("This is the API documentation for the Task List application. " +
                                "Contains methods to create, retrieve, update and delete tasks within the application."));
    }
}