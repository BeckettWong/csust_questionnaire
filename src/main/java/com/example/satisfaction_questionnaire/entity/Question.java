package com.example.satisfaction_questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
  private Integer id;
  
  private Integer surveyId;
  
  private String questionType;
  
  private String questionDescription;
  
  private Integer questionSort;
  
  private String requiredFlag;

}
