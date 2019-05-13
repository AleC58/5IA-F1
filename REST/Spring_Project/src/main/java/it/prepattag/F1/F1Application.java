package it.prepattag.F1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class F1Application {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /*
            Configurazione del servizio per consentire a qualsiasi dominio di fare richieste CORS (cross domain resource sharing)
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/get/*")
                        .allowedOrigins("*")
                        .allowedMethods("GET");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(F1Application.class, args);
    }
}
