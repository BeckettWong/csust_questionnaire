package com.example.satisfaction_questionnaire.service;

import com.example.satisfaction_questionnaire.entity.WebUser;

public interface WebUserService {
  WebUser getWebUser(String paramString);
  
  boolean updatePassword(String paramString1, String paramString2);
}
