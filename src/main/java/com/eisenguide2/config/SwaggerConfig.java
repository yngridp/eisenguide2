package com.eisenguide2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Eisenguide Gestão Eficiente")
                        .version("1.0.0")
                        .description("Projeto API Spring - Eisenguide")
                        .contact(new Contact()
                                .name("Julia Nunes, Giovanna Rodrigues, Yngrid Padilha")
                                .url("https://github.com/yngridp")
                                .email("yngrid_padilha@hotmail.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache License Version 2.0")
                                .url("https://github.com/yngridp"))
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

    private ApiResponses getApiResponses() {
        ApiResponses responses = new ApiResponses();

        responses.addApiResponse("200", new ApiResponse().description("Sucesso!"));
        responses.addApiResponse("201", new ApiResponse().description("Criado!"));
        responses.addApiResponse("400", new ApiResponse().description("Erro na requisição!"));
        responses.addApiResponse("401", new ApiResponse().description("Não Autorizado!"));
        responses.addApiResponse("403", new ApiResponse().description("Proibido!"));
        responses.addApiResponse("404", new ApiResponse().description("Não Encontrado!"));
        responses.addApiResponse("500", new ApiResponse().description("Erro no servidor!"));

        return responses;
    }
}
