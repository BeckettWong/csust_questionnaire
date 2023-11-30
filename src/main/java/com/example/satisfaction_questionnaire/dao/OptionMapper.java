package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Option;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OptionMapper {
  Integer getOptionId(Integer questionId, String optionName);
  
  List<Option> getQuestionOption(Integer questionId);
  
  boolean deleteOption(Integer optionId);
  
  void deleteBatchOption(@Param("optionIds") List<Integer> optionIds);
  
  boolean deleteByQuestionId(Integer questionId);
  
  boolean updateOption(Option option);
  
  boolean addOption(Option option);
  
  Integer getOptionIdByQuestionId(Integer questionId);
}
