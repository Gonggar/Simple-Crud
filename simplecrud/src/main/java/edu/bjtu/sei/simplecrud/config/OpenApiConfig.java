package edu.bjtu.sei.simplecrud.config;

//import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                		.title("Contact API")
                		.description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
                        .version("0.1 Beta")
                        .contact(new io.swagger.v3.oas.models.info.Contact().url("http://www.xxxx.edu.cn").name("admin:王大海").email("wangdh@mail.xxxx.edu.cn"))
);
        }
}