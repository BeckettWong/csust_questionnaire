package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.StudentMapper;
import com.example.satisfaction_questionnaire.entity.Student;
import com.example.satisfaction_questionnaire.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
  @Autowired
  StudentMapper studentMapper;
  
  public boolean addStudentInfo(Student student) {
    return this.studentMapper.addStudentInfo(student);
  }
  
  public Integer getStudentIdByOpenIdAndName(String openId, String questionnaireName) {
    return this.studentMapper.getStudentIdByOpenIdAndName(openId, questionnaireName);
  }
  
  public Student getStudentByOpenId(String openId) {
    return this.studentMapper.getStudentByOpenId(openId);
  }
  
  public Student getStudentByStudentId(Integer studentId) {
    return this.studentMapper.getStudentByStudentId(studentId);
  }
  
  public Integer getStudentId(String openId) {
    return this.studentMapper.getStudentId(openId);
  }
}
