package com.example.satisfaction_questionnaire.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOriginPatterns(new String[] { "*" }).allowedMethods(new String[] { "GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS" }).allowCredentials(true)
      .maxAge(3600L)
      .allowedHeaders(new String[] { "*" });
  }
}
