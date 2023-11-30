package com.example.satisfaction_questionnaire.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.satisfaction_questionnaire.entity.User;
import com.example.satisfaction_questionnaire.entity.WebUser;
import com.example.satisfaction_questionnaire.service.UserService;
import com.example.satisfaction_questionnaire.service.WebUserService;
import com.example.satisfaction_questionnaire.utils.Base64Utils;
import com.example.satisfaction_questionnaire.utils.JwtTokenUtil;
import com.example.satisfaction_questionnaire.utils.ResultEntity;
import com.example.satisfaction_questionnaire.utils.WeChatUtil;
import com.example.satisfaction_questionnaire.vo.Vo;
import com.example.satisfaction_questionnaire.vo.WebUserVo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  WebUserService webUserService;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @PostMapping("wx/login")
  public ResultEntity user_login(@RequestBody Vo vo) {
    JSONObject SessionKeyOpenId = WeChatUtil.getSessionKeyOrOpenId(vo.getCode());
    String openid = SessionKeyOpenId.getString("openid");
    String sessionKey = SessionKeyOpenId.getString("session_key");
    User user = userService.getUserByOpenId(openid);
    if (user == null)
      userService.addUser(openid);
    return ResultEntity.successWithData(Base64Utils.encode(openid));
  }

  @PostMapping("user/login")
  public ResultEntity login(@RequestBody WebUserVo webUser) {
    WebUser webuser = webUserService.getWebUser(webUser.getUserId());
    if (webuser == null)
      return ResultEntity.failed("没有此用户!!!");
    if (webuser.getUserpassword().equals(webUser.getUserpassword())) {
      String token = jwtTokenUtil.createToken(webuser.getUserId(), "admin");
      Map<String, String> tokenMap = new HashMap<>();
      tokenMap.put("token", token);
      tokenMap.put("tokenHead", "Authorization");
      return ResultEntity.successWithData(tokenMap);
    }
    return ResultEntity.failed("登录失败");
  }
}
