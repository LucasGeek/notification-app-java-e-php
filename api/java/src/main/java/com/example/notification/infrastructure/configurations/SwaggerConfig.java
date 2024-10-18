package com.example.notification.infrastructure.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Lucas Albuquerque",
                        email = "lucas.albuquerque@gmail.com",
                        url = "https://www.linkedin.com/in/lucasgeek",
                        extensions = {
                                @Extension(name = "x-contato", properties = {}),
                        }),
                title = "API de Notificações em Java",
                version = "1.0.0",
                description = "Documentação da API de Notificações em Spring Boot"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor Local"
                ),
        }
)
@SecurityScheme(name = "JSON Web Token", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(@SuppressWarnings("null") ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
        }
}