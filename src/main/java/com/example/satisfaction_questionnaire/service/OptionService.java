package com.example.satisfaction_questionnaire.service;

import org.springframework.stereotype.Repository;

@Repository
public interface OptionService {

  boolean addOption(Integer questionId, String[] options);
  
  Integer getOptionId(Integer questionId, String optionName);
  
  String[] getOptionNameByQuestionId(Integer questionId);
  
  Integer getOptionIdByQuestionId(Integer questionId);
  
  boolean addOptionOnly(Integer questionId, String optionName);
}
