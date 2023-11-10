package com.example.satisfaction_questionnaire.service;

import com.example.satisfaction_questionnaire.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentService {
  boolean addStudentInfo(Student paramStudent);
  
  Integer getStudentIdByOpenIdAndName(String paramString1, String paramString2);
  
  Student getStudentByOpenId(String paramString);
  
  Student getStudentByStudentId(Integer paramInteger);
  
  Integer getStudentId(String paramString);
}
