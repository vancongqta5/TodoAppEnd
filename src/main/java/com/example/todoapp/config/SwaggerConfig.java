package com.example.todoapp.config;

import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String DEFAULT_INCLUDE_PATTERN = "/v1/tasks/.*";
    private static final String API_TITLE = "TodoApp API";
    private static final String API_DESCRIPTION = "API for managing tasks in a Todo application";
    private static final String API_VERSION = "1.0";
    private static final String API_TERMS_OF_SERVICE_URL = "https://example.com/terms-of-service";
    private static final Contact API_CONTACT = new Contact("TodoApp Support", "https://example.com/support",
            "support@example.com");
    private static final String API_LICENSE = "Apache License Version 2.0";
    private static final String API_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
    private static final List<ResponseMessage> DEFAULT_RESPONSE_MESSAGES = List.of(
            new ResponseMessageBuilder().code(500).message("Internal Server Error")
                    .responseModel(new ModelRef("Error")).build(),
            new ResponseMessageBuilder().code(403).message("Forbidden").build(),
            new ResponseMessageBuilder().code(401).message("Unauthorized").build());


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.todoapp.controllers"))
                .paths(PathSelectors.regex("/v1/tasks.*"))
                .build()
                .apiInfo(apiEndPointsInfo())
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Todo App REST API")
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .termsOfServiceUrl(API_TERMS_OF_SERVICE_URL)
                .contact(API_CONTACT)
                .license(API_LICENSE)
                .licenseUrl(API_LICENSE_URL)
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }
}
