package com.melikecelen.stringdiffanalyzer.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {
        @Server(url = "http://localhost:8080/", description = "Localhost")
})
@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI configureApiDocs() {
        return new OpenAPI()
                .info(new Info().title("String Diff Analyzer API")
                        .description(
                                "String diff analyzer api"
                        )
                        .version("v1"));
    }
}
