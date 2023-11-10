package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Questionnaire;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionnaireMapper {
  boolean addQuestionnaire(Questionnaire paramQuestionnaire);
  
  boolean deleteQuestionnaire(Integer paramInteger);
  
  Integer[] getSurveyIdByName(String paramString);
  
  List<String> getQuestionnairesTargetByName(String paramString);
  
  List<String> getQuestionName();
  
  String getQuestionStatusByName(String paramString);
  
  List<String> getQuestionName2(String paramString);
  
  boolean updateQuestionAndStatus(String paramString1, String paramString2);
}
