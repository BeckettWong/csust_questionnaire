package com.example.satisfaction_questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation implements Serializable {
  private Integer id;
  private Integer userId;
  
  private Integer optionId;
  
  private String optionContent;
  
  private String userStatus;

  public Relation(Integer userId, Integer optionId, String optionContent, String userStatus) {
    this.userId = userId;
    this.optionId = optionId;
    this.optionContent = optionContent;
    this.userStatus = userStatus;
  }
}
