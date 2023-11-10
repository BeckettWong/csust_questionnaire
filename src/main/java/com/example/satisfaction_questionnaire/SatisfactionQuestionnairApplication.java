package com.example.satisfaction_questionnaire;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan({"com.example.satisfaction_questionnaire.dao"})
public class SatisfactionQuestionnairApplication {
  public static void main(String[] args) {
    SpringApplication.run(com.example.satisfaction_questionnaire.SatisfactionQuestionnairApplication.class, args);
  }
}
