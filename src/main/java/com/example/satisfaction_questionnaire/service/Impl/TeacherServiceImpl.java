package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.TeacherMapper;
import com.example.satisfaction_questionnaire.entity.Teacher;
import com.example.satisfaction_questionnaire.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
  @Autowired
  TeacherMapper teacherMapper;

  public boolean addTeacherInfo(Teacher teacher) {
    return teacherMapper.addTeacherInfo(teacher);
  }

  public Integer getTeacherId(String openId) {
    return teacherMapper.getTeacherId(openId);
  }

  public Integer getTeacherIdByOpenIdAndName(String openId, String questionnaireName) {
    return teacherMapper.getTeacherIdByOpenIdAndName(openId, questionnaireName);
  }

  public Teacher getTeacherByOpenId(String openId) {
    return teacherMapper.getTeacherByOpenId(openId);
  }

  public Teacher getTeacherByTeacherId(Integer teacherId) {
    return teacherMapper.getTeacherByTeacherId(teacherId);
  }
}
