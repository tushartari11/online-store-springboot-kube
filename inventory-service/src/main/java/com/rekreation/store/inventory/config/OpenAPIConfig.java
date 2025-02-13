package com.rekreation.store.inventory.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
  @Bean
  public OpenAPI inventoryServiceOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Inventory Service API")
                .version("1.0.0")
                .description("This is the Rest API for the Inventory Service")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(
            new ExternalDocumentation().description("You can refer to the documentation here"));
  }
}
