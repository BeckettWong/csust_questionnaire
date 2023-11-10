package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Describe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DescribeMapper {
  boolean addDescribe(Describe paramDescribe);
  
  String getDescribe(String paramString1, String paramString2);
}
