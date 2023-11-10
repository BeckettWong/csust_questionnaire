package com.example.satisfaction_questionnaire.service;

import org.springframework.stereotype.Repository;

@Repository
public interface OptionService {
  boolean addOption(Integer paramInteger, String[] paramArrayOfString);
  
  Integer getOptionId(Integer paramInteger, String paramString);
  
  String[] getOptionNameByQuestionId(Integer paramInteger);
  
  Integer getOptionIdByQuestionId(Integer paramInteger);
  
  boolean addOptionOnly(Integer paramInteger, String paramString);
}
