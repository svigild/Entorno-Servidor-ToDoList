package com.todolist.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configura Swagger para que los endpoints se puedan probar con el token JWT
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ToDo List API")
                        .version("1.0")
                        .description("API REST para gestionar una lista de tareas con Spring Boot y JWT. "
                                + "Primero haz login en /auth/login para obtener el token, "
                                + "luego usa el boton Authorize para pegarlo. "
                                + "Usuarios de prueba: admin/1234, gestor/1234, usuario/1234"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Introduce el token JWT obtenido en /auth/login")));
    }
}
