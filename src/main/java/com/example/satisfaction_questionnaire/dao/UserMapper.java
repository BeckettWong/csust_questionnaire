package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  boolean addUser(User paramUser);
  
  User getUserByOpenId(String paramString);
  
  String getOpenIdByUserId(Integer paramInteger);
}
