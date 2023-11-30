package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Teacher;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper {
  boolean addTeacherInfo(Teacher teacher);
  
  Integer getTeacherIdByOpenIdAndName(String openId, String questionnaireName);
  
  Teacher getTeacherByOpenId(String openId);
  
  Integer getTeacherId(String openId);
  
  Teacher getTeacherByTeacherId(Integer teacherId);
  
  List<Integer> getStudentIdByCampus(String campus, String questionnaireName);
  
  List<Integer> getTeacherIdBySex(String sex, String questionnaireName);
  
  List<Integer> getTeacherIdBySort(String sort, String questionnaireName);
}
