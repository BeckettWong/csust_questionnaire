package com.example.satisfaction_questionnaire.vo;

import lombok.Data;

import java.util.Arrays;

@Data
public class ResultVo {
  private String questionnaireName;
  
  private String[] surveyName;
  
  private String[][] resultNum;
  

}
