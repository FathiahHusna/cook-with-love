package org.cook.with.love.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    /*
     * View in browser: http://localhost:8080/swagger-ui/index.html
     */

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");

        Contact myContact = new Contact();
        myContact.setName("Fathiah Husna");
        myContact.setEmail("somermail@gmail.com");

        Info information = new Info()
                .title("Cook with Love API")
                .version("1.0")
                .description("This API exposes endpoints to manage recipes.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }

}
