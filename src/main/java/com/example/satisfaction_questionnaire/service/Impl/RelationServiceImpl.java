package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.RelationMapper;
import com.example.satisfaction_questionnaire.dao.StudentMapper;
import com.example.satisfaction_questionnaire.dao.TeacherMapper;
import com.example.satisfaction_questionnaire.entity.Relation;
import com.example.satisfaction_questionnaire.service.RelationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationServiceImpl implements RelationService {
  @Autowired
  RelationMapper relationMapper;
  
  @Autowired
  StudentMapper studentMapper;
  
  @Autowired
  TeacherMapper teacherMapper;
  
  public boolean addRelation(Integer userID, Integer optionID, String userStatus) {
    Relation relation = new Relation();
    relation.setUserId(userID);
    relation.setOptionId(optionID);
    relation.setUserStatus(userStatus);
    return this.relationMapper.addRelation(relation);
  }
  
  public Integer getNum(Integer optionId) {
    return Integer.valueOf(this.relationMapper.getOptionCount(optionId.intValue()));
  }
  
  public Integer getTeacherNum(Integer optionId) {
    return Integer.valueOf(this.relationMapper.getTeacherOptionCount(optionId.intValue()));
  }
  
  public Integer getStudentNum(Integer optionId) {
    return Integer.valueOf(this.relationMapper.getStudentOptionCount(optionId.intValue()));
  }
  
  public Integer getCampus(Integer optionId, String campus, String questionnaireName) {
    List<Integer> list1 = this.studentMapper.getStudentIdByCampus(campus, questionnaireName);
    Integer num = Integer.valueOf(0);
    for (Integer id : list1)
      num = Integer.valueOf(num.intValue() + this.relationMapper.getAcademyOptionCount(optionId, id).intValue()); 
    List<Integer> list2 = this.teacherMapper.getStudentIdByCampus(campus, questionnaireName);
    for (Integer id : list2)
      num = Integer.valueOf(num.intValue() + this.relationMapper.getAcademyOptionCount(optionId, id).intValue()); 
    return num;
  }
  
  public Integer getAcademyNum(Integer optionId, String academyName) {
    List<Integer> list = this.studentMapper.getStudentIdByAcademyName(academyName);
    Integer num = Integer.valueOf(0);
    for (Integer id : list)
      num = Integer.valueOf(num.intValue() + this.relationMapper.getAcademyOptionCount(optionId, id).intValue()); 
    return num;
  }
  
  public List<Relation> getStudentOptionContent(Integer optionId) {
    return this.relationMapper.getStudentOptionContent(optionId);
  }
  
  public List<Relation> getTeacherOptionContent(Integer optionId) {
    return this.relationMapper.getTeacherOptionContent(optionId);
  }
  
  public boolean addRelation2(Integer userID, Integer optionID, String optionContent, String userStatus) {
    Relation relation = new Relation();
    relation.setUserId(userID);
    relation.setOptionId(optionID);
    relation.setUserStatus(userStatus);
    relation.setOptionContent(optionContent);
    return this.relationMapper.addRelation(relation);
  }
  
  public Integer getSexNum(Integer optionId, String sex, String questionnaireName) {
    List<Integer> list1 = this.studentMapper.getStudentIdBySex(sex, questionnaireName);
    Integer num = Integer.valueOf(0);
    for (Integer id : list1)
      num = Integer.valueOf(num.intValue() + this.relationMapper.getAcademyOptionCount(optionId, id).intValue()); 
    List<Integer> list2 = this.teacherMapper.getTeacherIdBySex(sex, questionnaireName);
    for (Integer id : list2)
      num = Integer.valueOf(num.intValue() + this.relationMapper.getAcademyOptionCount(optionId, id).intValue()); 
    return num;
  }
  
  public Integer getGradeNum(Integer optionId, String grade, String questionnaireName) {
    List<Integer> list = this.studentMapper.getStudentIdByGradeName(grade, questionnaireName);
    Integer num = Integer.valueOf(0);
    for (Integer id : list)
      num = Integer.valueOf(num.intValue() + this.relationMapper.getAcademyOptionCount(optionId, id).intValue()); 
    return num;
  }
  
  public Integer getSortNum(Integer optionId, String sort, String questionnaireName) {
    List<Integer> list = this.teacherMapper.getTeacherIdBySort(sort, questionnaireName);
    Integer num = Integer.valueOf(0);
    for (Integer id : list)
      num = Integer.valueOf(num.intValue() + this.relationMapper.getAcademyOptionCount(optionId, id).intValue()); 
    return num;
  }
}
