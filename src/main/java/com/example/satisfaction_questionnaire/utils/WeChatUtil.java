package com.example.satisfaction_questionnaire.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.satisfaction_questionnaire.utils.HttpClientUtil;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {
  public static JSONObject getSessionKeyOrOpenId(String code) {
    String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
    Map<String, String> requestUrlParam = new HashMap<>();
    requestUrlParam.put("appid", "wx900e15fea921fe3c");
    requestUrlParam.put("secret", "53c8d3378c495bfeae78523e04a7a173");
    requestUrlParam.put("js_code", code);
    requestUrlParam.put("grant_type", "authorization_code");
    JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam));
    return jsonObject;
  }
}
