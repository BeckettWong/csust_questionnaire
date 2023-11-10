package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Student;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
  boolean addStudentInfo(Student paramStudent);
  
  Integer getStudentIdByOpenIdAndName(String paramString1, String paramString2);
  
  List<Integer> getStudentIdByAcademyName(String paramString);
  
  List<Integer> getStudentIdBySex(String paramString1, String paramString2);
  
  List<Integer> getStudentIdByCampus(String paramString1, String paramString2);
  
  Student getStudentByOpenId(String paramString);
  
  Student getStudentByStudentId(Integer paramInteger);
  
  Integer getStudentId(String paramString);
  
  List<Integer> getStudentIdByGradeName(String paramString1, String paramString2);
}
