package com.example.satisfaction_questionnaire.utils;

import com.example.satisfaction_questionnaire.entity.User;
import com.example.satisfaction_questionnaire.utils.JwtTokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {
  private static final Logger log = LoggerFactory.getLogger(com.example.satisfaction_questionnaire.utils.LoginInterceptor.class);
  
  public static ThreadLocal<User> loginUser = new ThreadLocal<>();
  
  @Autowired
  JwtTokenUtil jwtTokenUtil;
  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String method = request.getMethod();
    if ("OPTIONS".equals(method))
      return true; 
    log.info("preHandler========>" + request.getRequestURI());
    String requestURI = request.getRequestURI();
    String authHeader = "Bearer " + request.getHeader("Authorization");
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    boolean match1 = antPathMatcher.match("/questionnaire/**", requestURI);
    if (null != authHeader && authHeader.startsWith("Bearer")) {
      String authToken = authHeader.substring("Bearer".length());
      if (this.jwtTokenUtil.checkToken(authToken)) {
        String authority = this.jwtTokenUtil.getAuthority(authToken);
        if (match1 && 
          authority.equals("admin"))
          return true; 
      } 
    } 
    return false;
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {}
  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {}
}
