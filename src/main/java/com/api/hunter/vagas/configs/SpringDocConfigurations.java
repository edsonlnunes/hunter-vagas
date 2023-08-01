package com.api.hunter.vagas.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Hunter Vagas API")
                        .description("API para o sistema de vagas hunter")
                        .summary("Alguma coisa deve aparecer em algum lugar")
                        .contact(new Contact()
                                .name("Édson Martins")
                                .email("edson.martins@growdev.com.br")));
    }
}
