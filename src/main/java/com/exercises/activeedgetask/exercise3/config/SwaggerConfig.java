package com.exercises.activeedgetask.exercise3.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                description = "Open API documentation for Stock API",
                title = "Stock API Application",
                contact = @Contact(name = "Olawale Agboola",
                        email = "agboolawale8@gmail.com",
                        url = "https://www.github.com/Realwale"),
                version = "1.0",
                license = @License(name = "Apache License", url = "https://www.apache.org/licenses/LICENSE-2"),
                termsOfService = "Terms of Service"
        )
)
public class SwaggerConfig {
}
