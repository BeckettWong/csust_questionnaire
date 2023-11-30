package com.example.satisfaction_questionnaire.service;

import com.example.satisfaction_questionnaire.entity.Relation;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationService{
  boolean addRelation(Integer userID, Integer optionID, String userStatus);
  
  Integer getNum(Integer optionId);
  
  Integer getTeacherNum(Integer optionId);
  
  Integer getStudentNum(Integer optionId);
  
  Integer getAcademyNum(Integer optionId, String AcademyName);
  
  Integer getSexNum(Integer paramInteger, String paramString1, String paramString2);
  
  List<Relation> getStudentOptionContent(Integer optionId);
  
  List<Relation> getTeacherOptionContent(Integer optionId);
  
  boolean addRelation2(Integer userID, Integer optionID,String optionContent, String userStatus);
  
  Integer getCampus(Integer paramInteger, String paramString1, String paramString2);
  
  Integer getGradeNum(Integer paramInteger, String paramString1, String paramString2);
  
  Integer getSortNum(Integer paramInteger, String paramString1, String paramString2);

  boolean addRelations(List<Relation> relations);
}
