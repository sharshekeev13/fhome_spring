package com.example.fhome.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenApi(){
        Contact contact = new Contact();
        contact.setEmail("fhomekgz@gmail.com");
        contact.setName("From Home");
        Info info = new Info()
                .title("FHome - документация к запросам")
                .version("1.0")
                .contact(contact)
                .description("Это докумментация к конечным точкам для запросов на сервер FHome");
        return new OpenAPI().info(info);
    }
}
