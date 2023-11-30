package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.WebUserMapper;
import com.example.satisfaction_questionnaire.entity.WebUser;
import com.example.satisfaction_questionnaire.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebUserServiceImpl implements WebUserService {
  @Autowired
  WebUserMapper webUserMapper;

  public WebUser getWebUser(String userId) {
    return webUserMapper.getWebUser(userId);
  }

  public boolean updatePassword(String userId, String password) {
    return webUserMapper.updatePassWord(userId, password);
  }
}
