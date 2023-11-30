package com.example.satisfaction_questionnaire.service;

import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireService {
  boolean addQuestionnaire(String  questionnaireName, Integer surveyId,String questionnaireTarget);
  
  Integer[] getSurveyIdByName(String questionnaireName);
  
  String getQuestionnairesTargetByName(String questionnaireName);
  
  String[] getQuestionAndStatus();
  
  String[] getQuestionAndStatus2();
  
  boolean updateQuestionAndStatus(String questionnaireName, String questionnaireStatus);
}
