package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.OptionMapper;
import com.example.satisfaction_questionnaire.entity.Option;
import com.example.satisfaction_questionnaire.service.OptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements OptionService {
  @Autowired
  OptionMapper optionMapper;

  public boolean addOption(Integer questionId, String[] options) {
    for (String str : options) {
      Option option = new Option();
      option.setQuestionId(questionId);
      option.setOptionName(str);
      if (!optionMapper.addOption(option))
        return false;
    }
    return true;
  }

  public Integer getOptionId(Integer questionId, String optionName) {
    return optionMapper.getOptionId(questionId, optionName);
  }

  public String[] getOptionNameByQuestionId(Integer questionId) {
    List<Option> list = optionMapper.getQuestionOption(questionId);
    String[] optionName = new String[list.size()];
    int index = -1;
    for (Option option : list)
      optionName[++index] = option.getOptionName();
    return optionName;
  }

  public Integer getOptionIdByQuestionId(Integer questionId) {
    return optionMapper.getOptionIdByQuestionId(questionId);
  }

  public boolean addOptionOnly(Integer questionId, String optionName) {
    Option option = new Option();
    option.setQuestionId(questionId);
    option.setOptionName(optionName);
    return optionMapper.addOption(option);
  }
}
