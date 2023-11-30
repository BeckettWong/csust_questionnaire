package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.WebUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebUserMapper {
  WebUser getWebUser(String userId);
  
  boolean updatePassWord(String userpassword, String userId);
}
