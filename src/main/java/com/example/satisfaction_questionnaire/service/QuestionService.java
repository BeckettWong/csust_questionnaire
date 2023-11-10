package com.example.satisfaction_questionnaire.service;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionService {
  boolean addQuestion(Integer paramInteger, String paramString1, String paramString2);
  
  Integer getQuestionIdByDescription(String paramString, Integer paramInteger);
  
  Integer[] getQuestionIdBySurveyId(Integer paramInteger);
  
  String getQuestionDescriptionByQuestionId(Integer paramInteger);
  
  String getQuestionTypeByQuestionId(Integer paramInteger);
  
  List<Integer> getQuestionIdsByType(List<Integer> paramList);
}
