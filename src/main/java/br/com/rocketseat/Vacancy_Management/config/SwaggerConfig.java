package br.com.rocketseat.Vacancy_Management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Vacancy Management API").description("Api para gerir vagas de emprego").version("1.0"))
                .schemaRequirement("jwt", securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme().name("jwt").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    }
}

