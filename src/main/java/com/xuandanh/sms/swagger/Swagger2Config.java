package com.xuandanh.sms.swagger;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securityContexts(Collections.singletonList(securityContext()))
//                .securitySchemes(Collections.singletonList(apiKey()))
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "My REST API",
//                "Some custom description of API.",
//                "1.0",
//                "Terms of service",
//                new Contact("Sallo Szrajbman", "www.baeldung.com", "salloszraj@gmail.com"),
//                "License of API",
//                "API license URL",
//                Collections.emptyList());
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("jwtToken", "Authorization", "header");
//    }
//
//    Parameter authHeader = new ParameterBuilder()
//            .parameterType("header")
//            .name("Authorization")
//            .modelRef(new ModelRef("string"))
//            .build();
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
//    }

//    @Bean
//    public Docket api() {
//        ParameterBuilder paramBuilder = new ParameterBuilder();
//        List<Parameter> params = new ArrayList<>();
//        paramBuilder.name("Authorization").modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build();
//        params.add(paramBuilder.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(params)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo())
//                .consumes(new HashSet<String>(Collections.singletonList("application/json")))
//                .produces(new HashSet<String>(Collections.singletonList("application/json")));
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "My API",
//                "My API desc",
//                "1.0",
//                "Terms of service",
//                new Contact("hijazyr", "", ""),
//                "License of API", "API license URL", Collections.emptyList());
//    }
//    private ApiKey apiKey() {
//        return new ApiKey("Bearer", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth())
//                .forPaths(PathSelectors.any()).build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope(
//                "global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections.singletonList(new SecurityReference("Bearer",
//                authorizationScopes));
//    }


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xuandanh.sms.restapi"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }


//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT",
                authorizationScopes));
    }

//    @Bean
//    public SecurityConfiguration security() {
//        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
//                .additionalQueryStringParams(null)
//                .useBasicAuthenticationWithAccessCodeGrant(false).build();
//    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null, null, null,
                "Docserver2_fwk", // app name
                "BEARER", // api key value
                ApiKeyVehicle.HEADER, "Authorization", ",");
    }
}