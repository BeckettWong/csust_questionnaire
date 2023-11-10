package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.QuestionnaireMapper;
import com.example.satisfaction_questionnaire.entity.Questionnaire;
import com.example.satisfaction_questionnaire.service.QuestionnaireService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
  @Autowired
  QuestionnaireMapper questionnaireMapper;
  
  public boolean addQuestionnaire(String questionnaireName, Integer surveyId, String questionnaireTarget) {
    Questionnaire questionnaire = new Questionnaire();
    questionnaire.setQuestionnaireName(questionnaireName);
    questionnaire.setSurveyId(surveyId);
    questionnaire.setQuestionnaireTarget(questionnaireTarget);
    questionnaire.setQuestionnaireStatus("暂存");
    return this.questionnaireMapper.addQuestionnaire(questionnaire);
  }
  
  public Integer[] getSurveyIdByName(String questionnaireName) {
    return this.questionnaireMapper.getSurveyIdByName(questionnaireName);
  }
  
  public String getQuestionnairesTargetByName(String questionnaireName) {
    return this.questionnaireMapper.getQuestionnairesTargetByName(questionnaireName).get(0);
  }
  
  public String[] getQuestionAndStatus() {
    List<String> names = this.questionnaireMapper.getQuestionName();
    String[] strs = names.<String>toArray(new String[names.size()]);
    String[] result = new String[strs.length];
    for (int i = 0; i < names.size(); i++)
      result[i] = "name:" + strs[i] + "<>status:" + this.questionnaireMapper.getQuestionStatusByName(strs[i]) + "<>target:" + getQuestionnairesTargetByName(strs[i]); 
    return result;
  }
  
  public String[] getQuestionAndStatus2() {
    List<String> names = this.questionnaireMapper.getQuestionName2("发布");
    String[] strs = names.<String>toArray(new String[names.size()]);
    String[] result = new String[strs.length];
    for (int i = 0; i < names.size(); i++)
      result[i] = "name:" + strs[i] + "<>status:" + this.questionnaireMapper.getQuestionStatusByName(strs[i]) + "<>target:" + getQuestionnairesTargetByName(strs[i]); 
    return result;
  }
  
  public boolean updateQuestionAndStatus(String questionnaireName, String questionnaireStatus) {
    return this.questionnaireMapper.updateQuestionAndStatus(questionnaireName, questionnaireStatus);
  }
}
