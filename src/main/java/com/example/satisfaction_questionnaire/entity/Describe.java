package com.example.satisfaction_questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Describe {
  private Integer id;
  
  private String questionnaireName;
  
  private String description;
  
  private String questionnaireTarget;

}
