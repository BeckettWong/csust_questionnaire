package com.example.satisfaction_questionnaire.service;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionService {
  boolean addQuestion(Integer surveyId, String questionType, String questionDescription);
  
  Integer getQuestionIdByDescription(String questionDescription,Integer surveyId);
  
  Integer[] getQuestionIdBySurveyId(Integer surveyId);
  
  String getQuestionDescriptionByQuestionId(Integer questionId);
  
  String getQuestionTypeByQuestionId(Integer questionId);
  
  List<Integer> getQuestionIdsByType(List<Integer> surveyIds);
}
