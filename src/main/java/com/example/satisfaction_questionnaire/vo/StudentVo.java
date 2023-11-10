package com.example.satisfaction_questionnaire.vo;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class StudentVo {
  String campus;
  
  String garde;
  
  String sex;
  
  String academy;
  
  Timestamp createDate;
  
  String optionContent;
  
  String questionDescription;
  
  String sort;

}
