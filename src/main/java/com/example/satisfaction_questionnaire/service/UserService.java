package com.example.satisfaction_questionnaire.service;

import com.example.satisfaction_questionnaire.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
  boolean addUser(String paramString);
  
  User getUserByOpenId(String paramString);
  
  String getOpenIdByUserId(Integer paramInteger);
}
