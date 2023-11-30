package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Relation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationMapper{
  int getOptionCount(int OptionId);
  
  int getStudentOptionCount(int OptionId);
  
  int getTeacherOptionCount(int OptionId);
  
  boolean addRelation(Relation relation);
  
  Integer getAcademyOptionCount(Integer OptionId, Integer userId);
  
  List<Relation> getStudentOptionContent(Integer optionId);
  
  List<Relation> getTeacherOptionContent(Integer optionId);

  boolean addRelations(List<Relation> relations);
}
