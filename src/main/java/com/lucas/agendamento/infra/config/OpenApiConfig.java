package com.lucas.agendamento.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI agendamentoAPI() {
        return new OpenAPI()
                .info(
                            new Info().title("API de Gerenciamento de Agendamentos")
                                .description("""
                                        API REST para gerenciamento de pacientes e agendamentos de consultas.
                                        
                                        Desenvolvida com Java, Spring Boot e arquitetura baseada nos princípios da Clean Architecture.
                                        """)
                                .version("v1.0.0")
                                    .contact(new Contact()
                                            .name("Lucas Oliveira da Silva")
                                            .url("https://github.com/lucaso-silva")
                                            .email("lucs.osilv@gmail.com"))
                );
    }
}
