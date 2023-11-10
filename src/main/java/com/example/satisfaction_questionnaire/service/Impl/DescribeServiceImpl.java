package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.DescribeMapper;
import com.example.satisfaction_questionnaire.entity.Describe;
import com.example.satisfaction_questionnaire.service.DescribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescribeServiceImpl implements DescribeService {
  @Autowired
  DescribeMapper describeMapper;
  
  public boolean addDescribe(Describe describe) {
    return this.describeMapper.addDescribe(describe);
  }
  
  public String getDescribe(String questionnaireName, String questionnaireTarget) {
    return this.describeMapper.getDescribe(questionnaireName, questionnaireTarget);
  }
}
