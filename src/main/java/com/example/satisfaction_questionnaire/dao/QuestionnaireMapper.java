package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Questionnaire;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionnaireMapper {
  boolean addQuestionnaire(Questionnaire questionnaire);
  
  boolean deleteQuestionnaire(Integer questionnaireId);
  
  Integer[] getSurveyIdByName(String questionnaireName);
  
  List<String> getQuestionnairesTargetByName(String questionnaireName);
  
  List<String> getQuestionName();
  
  String getQuestionStatusByName(String questionnaireName);
  
  List<String> getQuestionName2(String status);
  
  boolean updateQuestionAndStatus(String questionnaireName, String questionnaireStatus);
}
