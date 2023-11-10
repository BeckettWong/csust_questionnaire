package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Survey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyMapper {
  boolean addSurvey(Survey paramSurvey);
  
  Integer getSurveyIdByName2(String paramString1, String paramString2);
  
  Integer getSurveyIdByName(String paramString);
  
  String getSuveyNameById(Integer paramInteger);
}
