package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Question;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionMapper {
  List<Integer> getQuestionIdBySurveyId(Integer surveyId);
  
  boolean addQuestion(Question question);
  
  boolean deleteQuestion(Integer questionId);
  
  Integer getQuestionIdByDescription(String questionDescription, Integer surveyId);
  
  String getDescriptionByQuestionId(Integer questionId);
  
  String getQuestionTypeByQuestionId(Integer questionId);
  
  List<Integer> getQuestionIdsByType(@Param("surveyIds") List<Integer> surveyIds);
}
