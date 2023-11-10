package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Question;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionMapper {
  List<Integer> getQuestionIdBySurveyId(Integer paramInteger);
  
  boolean addQuestion(Question paramQuestion);
  
  boolean deleteQuestion(Integer paramInteger);
  
  Integer getQuestionIdByDescription(String paramString, Integer paramInteger);
  
  String getDescriptionByQuestionId(Integer paramInteger);
  
  String getQuestionTypeByQuestionId(Integer paramInteger);
  
  List<Integer> getQuestionIdsByType(@Param("surveyIds") List<Integer> paramList);
}
