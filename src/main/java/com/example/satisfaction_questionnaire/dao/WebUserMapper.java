package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.WebUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebUserMapper {
  WebUser getWebUser(String paramString);
  
  boolean updatePassWord(String paramString1, String paramString2);
}
