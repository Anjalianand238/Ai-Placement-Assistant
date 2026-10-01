package com.placement.assistant.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;
import java.util.*;

@Configuration
public class CorsConfig {
    @Value("${cors.allowed-origins}") private String allowedOrigins;
    @Bean public CorsFilter corsFilter(){
        CorsConfiguration c=new CorsConfiguration();
        c.setAllowCredentials(true);
        c.setAllowedOrigins(List.of(allowedOrigins));
        c.setAllowedHeaders(List.of("*"));
        c.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
        c.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();
        s.registerCorsConfiguration("/**",c);
        return new CorsFilter(s);
    }
}
