package com.example.satisfaction_questionnaire.service;

import com.example.satisfaction_questionnaire.entity.Describe;
import org.springframework.stereotype.Repository;

@Repository
public interface DescribeService {
  boolean addDescribe(Describe describe);
  
  String getDescribe(String paramString1, String paramString2);
}
