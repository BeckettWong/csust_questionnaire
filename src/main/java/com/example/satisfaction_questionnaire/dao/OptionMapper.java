package com.example.satisfaction_questionnaire.dao;

import com.example.satisfaction_questionnaire.entity.Option;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OptionMapper {
  Integer getOptionId(Integer paramInteger, String paramString);
  
  List<Option> getQuestionOption(Integer paramInteger);
  
  boolean deleteOption(Integer paramInteger);
  
  void deleteBatchOption(@Param("optionIds") List<Integer> paramList);
  
  boolean deleteByQuestionId(Integer paramInteger);
  
  boolean updateOption(Option paramOption);
  
  boolean addOption(Option paramOption);
  
  Integer getOptionIdByQuestionId(Integer paramInteger);
}
