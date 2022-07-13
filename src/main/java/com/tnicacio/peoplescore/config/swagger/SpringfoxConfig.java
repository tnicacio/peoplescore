package com.tnicacio.peoplescore.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
@Import(SpringDataRestConfiguration.class)
public class SpringfoxConfig {

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(PersonDTO.class)
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tnicacio.peoplescore"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, responseListForGET())
                .globalResponses(HttpMethod.POST, responseListForPOST())
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "People Score API",
                "Serviço para cadastro de pessoas com score e suas regiões de afinidade.",
                "1.0",
                null,
                new Contact("Tiago Nicácio", "https://www.linkedin.com/in/tiagonic/", "nicacio.t92@gmail.com"),
                null,
                null,
                Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<Response> responseListForGET() {
        return List.of(
                new ResponseBuilder()
                        .code("200")
                        .description("OK")
                        .build(),
                new ResponseBuilder()
                        .code("204")
                        .description("No Content")
                        .build(),
                new ResponseBuilder()
                        .code("401")
                        .description("Unauthorized")
                        .build()
        );
    }

    private List<Response> responseListForPOST() {
        return List.of(
                new ResponseBuilder()
                        .code("201")
                        .description("Created")
                        .build(),
                new ResponseBuilder()
                        .code("400")
                        .description("Bad Request")
                        .build()
        );
    }


}
