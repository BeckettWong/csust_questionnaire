package com.example.satisfaction_questionnaire.vo;

import lombok.Data;

import java.util.Arrays;
@Data
public class QuestionOptionVo {
  private String[] basicInfo;
  
  private String questionnaireName;
  
  private String[][] questionAnswer;
  
  private String open_id;
  

}
