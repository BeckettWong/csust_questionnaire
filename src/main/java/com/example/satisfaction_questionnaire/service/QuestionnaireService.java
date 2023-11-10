package com.example.satisfaction_questionnaire.service;

import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireService {
  boolean addQuestionnaire(String paramString1, Integer paramInteger, String paramString2);
  
  Integer[] getSurveyIdByName(String paramString);
  
  String getQuestionnairesTargetByName(String paramString);
  
  String[] getQuestionAndStatus();
  
  String[] getQuestionAndStatus2();
  
  boolean updateQuestionAndStatus(String paramString1, String paramString2);
}
