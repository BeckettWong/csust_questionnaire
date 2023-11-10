package com.example.satisfaction_questionnaire.service;

import com.example.satisfaction_questionnaire.entity.Relation;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationService {
  boolean addRelation(Integer paramInteger1, Integer paramInteger2, String paramString);
  
  Integer getNum(Integer paramInteger);
  
  Integer getTeacherNum(Integer paramInteger);
  
  Integer getStudentNum(Integer paramInteger);
  
  Integer getAcademyNum(Integer paramInteger, String paramString);
  
  Integer getSexNum(Integer paramInteger, String paramString1, String paramString2);
  
  List<Relation> getStudentOptionContent(Integer paramInteger);
  
  List<Relation> getTeacherOptionContent(Integer paramInteger);
  
  boolean addRelation2(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2);
  
  Integer getCampus(Integer paramInteger, String paramString1, String paramString2);
  
  Integer getGradeNum(Integer paramInteger, String paramString1, String paramString2);
  
  Integer getSortNum(Integer paramInteger, String paramString1, String paramString2);
}
