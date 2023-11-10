package com.example.satisfaction_questionnaire.vo;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class AllVo {
  String sex;
  
  String campus;
  
  Timestamp createDate;
  
  String optionContent;
  
  String questionDescription;
  
  String Status;
  

}
