package com.example.satisfaction_questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire {
  private Integer id;
  
  private Integer surveyId;
  
  private String questionnaireName;
  
  private String questionnaireTarget;
  
  private String questionnaireStatus;

}
