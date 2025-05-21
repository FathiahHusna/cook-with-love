package org.cook.with.love.configuration;

import org.cook.with.love.constant.CookLoveConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Apply to all /api/** routes
                .allowedOrigins("http://localhost:3000")  // Allow requests from React frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow specific HTTP methods
                .allowedHeaders(CookLoveConstant.API_KEY_HEADER, CookLoveConstant.API_SECRET_HEADER, CookLoveConstant.CONTENT_TYPE_HEADER)  // Allow specific headers
                .exposedHeaders(CookLoveConstant.API_KEY_HEADER, CookLoveConstant.API_SECRET_HEADER, CookLoveConstant.CONTENT_TYPE_HEADER)  // Expose these headers to the frontend
                .allowCredentials(true)  // Allow credentials (cookies, etc.)
                .maxAge(3600);  // Cache preflight response for 1 hour
    }

}
