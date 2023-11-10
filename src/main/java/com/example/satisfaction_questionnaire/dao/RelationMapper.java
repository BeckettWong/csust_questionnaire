package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Relation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationMapper {
  int getOptionCount(int paramInt);
  
  int getStudentOptionCount(int paramInt);
  
  int getTeacherOptionCount(int paramInt);
  
  boolean addRelation(Relation paramRelation);
  
  Integer getAcademyOptionCount(Integer paramInteger1, Integer paramInteger2);
  
  List<Relation> getStudentOptionContent(Integer paramInteger);
  
  List<Relation> getTeacherOptionContent(Integer paramInteger);
}
