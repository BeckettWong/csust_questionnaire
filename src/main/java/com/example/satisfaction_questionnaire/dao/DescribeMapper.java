package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Describe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DescribeMapper {
  boolean addDescribe(Describe describe);
  
  String getDescribe(String questionnaireName, String questionnaireTarget);
}
