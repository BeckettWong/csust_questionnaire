package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Survey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyMapper {
  boolean addSurvey(Survey survey);
  
  Integer getSurveyIdByName2(String surveyName, String questionnaireName);
  
  Integer getSurveyIdByName(String surveyName);
  
  String getSuveyNameById(Integer id);
}
