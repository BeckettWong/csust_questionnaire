package com.example.satisfaction_questionnaire.service;

import org.springframework.stereotype.Repository;

@Repository
public interface SurveyService {
  boolean addSurvey(String paramString1, String paramString2);
  
  Integer getSurveyIdByName(String paramString1, String paramString2);
  
  Integer[] getSurveyIdBynames(String[] paramArrayOfString, String paramString);
  
  String[] getSurveyNameById(Integer[] paramArrayOfInteger);
}
