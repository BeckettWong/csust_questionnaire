package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Teacher;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper {
  boolean addTeacherInfo(Teacher paramTeacher);
  
  Integer getTeacherIdByOpenIdAndName(String paramString1, String paramString2);
  
  Teacher getTeacherByOpenId(String paramString);
  
  Integer getTeacherId(String paramString);
  
  Teacher getTeacherByTeacherId(Integer paramInteger);
  
  List<Integer> getStudentIdByCampus(String paramString1, String paramString2);
  
  List<Integer> getTeacherIdBySex(String paramString1, String paramString2);
  
  List<Integer> getTeacherIdBySort(String paramString1, String paramString2);
}
