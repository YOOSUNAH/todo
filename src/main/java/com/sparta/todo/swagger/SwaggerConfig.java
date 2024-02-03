package com.sparta.todo.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info();
        info.title("my-api")
            .version("1.0.0")
            .description("this is my swagger");

        return new OpenAPI().info(info);
    }
}
