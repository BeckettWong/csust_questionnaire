package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Student;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
  boolean addStudentInfo(Student student);
  
  Integer getStudentIdByOpenIdAndName(String openId, String questionnaireName);
  
  List<Integer> getStudentIdByAcademyName(String academyName);
  
  List<Integer> getStudentIdBySex(String sex, String questionnaireName);
  
  List<Integer> getStudentIdByCampus(String campus, String questionnaireName);
  
  Student getStudentByOpenId(String openId);
  
  Student getStudentByStudentId(Integer studentId);
  
  Integer getStudentId(String openId);
  
  List<Integer> getStudentIdByGradeName(String grade, String questionnaireName);
}
