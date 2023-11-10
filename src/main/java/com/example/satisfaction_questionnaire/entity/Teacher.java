package com.example.satisfaction_questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements Serializable {
  private Integer id;
  
  private String sex;
  
  private Timestamp createDate;
  
  private String campus;
  
  private String openId;
  
  private String questionnaireName;
  
  private String sort;

}
