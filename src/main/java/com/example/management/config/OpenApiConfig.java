package com.example.management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System",
                description = "Task Management System", version = "2.6.0",
                contact = @Contact(
                        name = "Anastasia",
                        email = "n.bogocharova@gmail.com"
                )
        )
)
public class OpenApiConfig {

}
