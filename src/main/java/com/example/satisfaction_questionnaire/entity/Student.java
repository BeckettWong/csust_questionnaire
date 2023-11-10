package com.example.satisfaction_questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
  private Integer id;
  
  private String campus;
  
  private String grade;
  
  private String sex;
  
  private String academyName;
  
  private Timestamp createDate;
  
  private String openId;
  
  private String questionniareName;

}
