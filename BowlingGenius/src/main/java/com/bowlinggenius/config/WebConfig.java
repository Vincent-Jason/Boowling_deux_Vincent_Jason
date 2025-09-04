package com.bowlinggenius.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Configuration CORS appliquée");
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // Utilisation de allowedOriginPatterns au lieu de allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);  // Désactivation de allowCredentials pour le moment
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("Configuration des ressources statiques");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
