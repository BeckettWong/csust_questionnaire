package com.example.satisfaction_questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey implements Serializable {
  private Integer id;
  
  private String surveyName;
  
  private String questionnaireName;
  
  private String status;
  
  private Timestamp createDate;
  
  private Timestamp updateDate;

}
