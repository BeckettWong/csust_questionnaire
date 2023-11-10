package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.UserMapper;
import com.example.satisfaction_questionnaire.entity.User;
import com.example.satisfaction_questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserMapper userMapper;
  
  public boolean addUser(String openId) {
    User user = new User();
    user.setOpenId(openId);
    return this.userMapper.addUser(user);
  }
  
  public User getUserByOpenId(String openId) {
    return null;
  }
  
  public String getOpenIdByUserId(Integer userId) {
    return this.userMapper.getOpenIdByUserId(userId);
  }
}
