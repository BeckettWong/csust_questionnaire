package com.example.satisfaction_questionnaire.service.Impl;

import com.example.satisfaction_questionnaire.dao.QuestionMapper;
import com.example.satisfaction_questionnaire.entity.Question;
import com.example.satisfaction_questionnaire.service.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
  @Autowired
  QuestionMapper questionMapper;
  
  public boolean addQuestion(Integer surveyId, String questionType, String questionDescription) {
    Question question = new Question();
    question.setSurveyId(surveyId);
    question.setQuestionType(questionType);
    question.setQuestionDescription(questionDescription);
    return this.questionMapper.addQuestion(question);
  }
  
  public Integer getQuestionIdByDescription(String questionDescription, Integer surveyId) {
    return this.questionMapper.getQuestionIdByDescription(questionDescription, surveyId);
  }
  
  public Integer[] getQuestionIdBySurveyId(Integer surveyId) {
    List<Integer> questionId = this.questionMapper.getQuestionIdBySurveyId(surveyId);
    int index = -1;
    Integer[] questionIdArr = new Integer[questionId.size()];
    for (Integer questionid : questionId)
      questionIdArr[++index] = questionid; 
    return questionIdArr;
  }
  
  public String getQuestionDescriptionByQuestionId(Integer questionId) {
    String description = this.questionMapper.getDescriptionByQuestionId(questionId);
    return description;
  }
  
  public String getQuestionTypeByQuestionId(Integer questionId) {
    String type = this.questionMapper.getQuestionTypeByQuestionId(questionId);
    return type;
  }
  
  public List<Integer> getQuestionIdsByType(List<Integer> surveyIds) {
    return this.questionMapper.getQuestionIdsByType(surveyIds);
  }
}
