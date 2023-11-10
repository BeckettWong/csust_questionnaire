package com.example.satisfaction_questionnaire.utils;

import com.example.satisfaction_questionnaire.utils.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
  @Autowired
  private LoginInterceptor loginInterceptor;
  
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor((HandlerInterceptor)this.loginInterceptor)
      .addPathPatterns(new String[] { "/questionnaire/**" }).excludePathPatterns(new String[] { "/user/**" }).excludePathPatterns(new String[] { "/wx/**" });
  }
}
