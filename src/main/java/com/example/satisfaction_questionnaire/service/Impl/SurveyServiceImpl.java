package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.SurveyMapper;
import com.example.satisfaction_questionnaire.entity.Survey;
import com.example.satisfaction_questionnaire.service.SurveyService;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl implements SurveyService {
  @Autowired
  SurveyMapper surveyMapper;

  public boolean addSurvey(String surveyName, String questionnaireName) {
    Survey survey = new Survey();
    survey.setSurveyName(surveyName);
    survey.setQuestionnaireName(questionnaireName);
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    survey.setStatus("0");
    survey.setCreateDate(timestamp);
    survey.setUpdateDate(timestamp);
    return surveyMapper.addSurvey(survey);
  }

  public Integer getSurveyIdByName(String surveyName, String questionnaireName) {
    return surveyMapper.getSurveyIdByName2(surveyName, questionnaireName);
  }

  public Integer[] getSurveyIdBynames(String[] surveyName, String questionnaireName) {
    Integer[] integer = new Integer[surveyName.length];
    int index = -1;
    for (String name : surveyName)
      integer[++index] = surveyMapper.getSurveyIdByName2(name, questionnaireName);
    return integer;
  }

  public String[] getSurveyNameById(Integer[] surveyId) {
    String[] surveyName = new String[surveyId.length];
    int index = -1;
    for (Integer id : surveyId)
      surveyName[++index] = surveyMapper.getSuveyNameById(id);
    return surveyName;
  }
}
