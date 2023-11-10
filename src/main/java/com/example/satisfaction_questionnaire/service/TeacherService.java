package com.example.satisfaction_questionnaire.service;

import com.example.satisfaction_questionnaire.entity.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherService {
  boolean addTeacherInfo(Teacher paramTeacher);
  
  Integer getTeacherIdByOpenIdAndName(String paramString1, String paramString2);
  
  Teacher getTeacherByOpenId(String paramString);
  
  Integer getTeacherId(String paramString);
  
  Teacher getTeacherByTeacherId(Integer paramInteger);
}
