package com.example.satisfaction_questionnaire.controller;

import com.example.satisfaction_questionnaire.entity.Student;
import com.example.satisfaction_questionnaire.entity.Teacher;
import com.example.satisfaction_questionnaire.service.DescribeService;
import com.example.satisfaction_questionnaire.service.OptionService;
import com.example.satisfaction_questionnaire.service.QuestionService;
import com.example.satisfaction_questionnaire.service.QuestionnaireService;
import com.example.satisfaction_questionnaire.service.RelationService;
import com.example.satisfaction_questionnaire.service.StudentService;
import com.example.satisfaction_questionnaire.service.SurveyService;
import com.example.satisfaction_questionnaire.service.TeacherService;
import com.example.satisfaction_questionnaire.utils.Base64Utils;
import com.example.satisfaction_questionnaire.utils.ResultEntity;
import com.example.satisfaction_questionnaire.vo.QuestionOptionVo;
import com.example.satisfaction_questionnaire.vo.QuestionnaireVo;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxController {
  @Autowired
  QuestionnaireService questionnaireService;
  
  @Autowired
  QuestionService questionService;
  
  @Autowired
  SurveyService surveyService;
  
  @Autowired
  OptionService optionService;
  
  @Autowired
  StudentService studentService;
  
  @Autowired
  TeacherService teacherService;
  
  @Autowired
  RelationService relationService;
  
  @Autowired
  protected HttpServletRequest request;
  
  @Autowired
  DescribeService describeService;
  
  @PostMapping({"wx/questionnaire/upload"})
  public ResultEntity UploadFilledQuestionnair(@RequestBody QuestionOptionVo questionOptionVo) {
    String[] basicInfo = questionOptionVo.getBasicInfo();
    String questionnaireName = questionOptionVo.getQuestionnaireName();
    String[][] questionAnswer = questionOptionVo.getQuestionAnswer();
    String open_id = Base64Utils.decode(questionOptionVo.getOpen_id());
    String status = basicInfo[0];
    if (status.equals("teacher")) {
      Teacher teacher = new Teacher();
      teacher.setSex(basicInfo[1]);
      teacher.setCampus(basicInfo[2]);
      teacher.setSort(basicInfo[3]);
      teacher.setOpenId(open_id);
      teacher.setQuestionnaireName(questionnaireName);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      teacher.setCreateDate(timestamp);
      if (questionAnswer.length == 5)
        if (this.teacherService.getTeacherId(open_id) == null) {
          this.teacherService.addTeacherInfo(teacher);
          Integer[] surveyID = this.questionnaireService.getSurveyIdByName(questionnaireName);
          for (int i = 0; i < surveyID.length; i++) {
            Integer[] questionId = this.questionService.getQuestionIdBySurveyId(surveyID[i]);
            for (int j = 0; j < questionId.length; j++) {
              String[] oldstr = questionAnswer[i][j].split("answer:");
              String type = oldstr[0].substring(5);
              if (type.equals("单选")) {
                Integer optionId = this.optionService.getOptionId(questionId[j], oldstr[1]);
                Integer userId = this.teacherService.getTeacherIdByOpenIdAndName(open_id, questionnaireName);
                this.relationService.addRelation(userId, optionId, "teacher");
              } else if (type.equals("多选")) {
                String[] newStr = oldstr[1].split("&&");
                for (String s : newStr) {
                  Integer optionId = this.optionService.getOptionId(questionId[j], s);
                  Integer userId = this.teacherService.getTeacherIdByOpenIdAndName(open_id, questionnaireName);
                  this.relationService.addRelation(userId, optionId, "teacher");
                } 
              } else if (type.equals("填空")) {
                Integer optionId = this.optionService.getOptionId(questionId[j], " ");
                Integer userId = this.teacherService.getTeacherIdByOpenIdAndName(open_id, questionnaireName);
                this.relationService.addRelation2(userId, optionId, oldstr[1], "teacher");
              } 
            } 
          } 
        }  
    } else if (status.equals("student")) {
      Student student = new Student();
      student.setSex(basicInfo[1]);
      student.setGrade(basicInfo[2]);
      student.setCampus(basicInfo[3]);
      student.setAcademyName(basicInfo[4]);
      student.setQuestionniareName(questionnaireName);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      student.setCreateDate(timestamp);
      student.setOpenId(open_id);
      if (questionAnswer.length == 6)
        if (this.studentService.getStudentId(open_id) == null) {
          this.studentService.addStudentInfo(student);
          Integer[] surveyID = this.questionnaireService.getSurveyIdByName(questionnaireName);
          for (int i = 0; i < surveyID.length; i++) {
            Integer[] questionId = this.questionService.getQuestionIdBySurveyId(surveyID[i]);
            for (int j = 0; j < questionId.length; j++) {
              String[] oldstr = questionAnswer[i][j].split("answer:");
              String type = oldstr[0].substring(5);
              if (type.equals("单选")) {
                Integer optionId = this.optionService.getOptionId(questionId[j], oldstr[1]);
                Integer userId = this.studentService.getStudentIdByOpenIdAndName(open_id, questionnaireName);
                this.relationService.addRelation(userId, optionId, "student");
              } else if (type.equals("多选")) {
                String[] newStr = oldstr[1].split("&&");
                for (String s : newStr) {
                  Integer optionId1 = this.optionService.getOptionId(questionId[j], s);
                  Integer userId = this.studentService.getStudentIdByOpenIdAndName(open_id, questionnaireName);
                  this.relationService.addRelation(userId, optionId1, "student");
                } 
              } else if (type.equals("填空")) {
                Integer optionId = this.optionService.getOptionId(questionId[j], " ");
                Integer userId = this.studentService.getStudentIdByOpenIdAndName(open_id, questionnaireName);
                this.relationService.addRelation2(userId, optionId, oldstr[1], "student");
              } 
            } 
          } 
        }  
    } 
    return ResultEntity.successWithoutData();
  }
  
  @GetMapping({"wx/questionnaire/get"})
  public ResultEntity GetQuestionnaire(String questionnaireName) {
    QuestionnaireVo questionnaireVo = new QuestionnaireVo();
    questionnaireVo.setQuestionnaireName(questionnaireName);
    String questionnairesTarget = this.questionnaireService.getQuestionnairesTargetByName(questionnaireName);
    questionnaireVo.setQuestionnaireTarget(questionnairesTarget);
    Integer[] surveyId = this.questionnaireService.getSurveyIdByName(questionnaireName);
    String[] surveyName = this.surveyService.getSurveyNameById(surveyId);
    questionnaireVo.setSurveyName(surveyName);
    String[][] questionOption = new String[surveyId.length][10];
    questionnaireVo.setDescription(this.describeService.getDescribe(questionnaireName, questionnairesTarget));
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = this.questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        StringBuffer sb = new StringBuffer();
        sb.append("question:");
        sb.append(this.questionService.getQuestionDescriptionByQuestionId(questionId[j]));
        sb.append("type:");
        String type = this.questionService.getQuestionTypeByQuestionId(questionId[j]);
        sb.append(type);
        if (!type.equals("填空")) {
          sb.append("option:");
          String[] option = this.optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break; 
            sb.append("&&");
          } 
        } 
        questionOption[i][j] = sb.toString();
      } 
    } 
    questionnaireVo.setQuestionOption(questionOption);
    return ResultEntity.successWithData(questionnaireVo);
  }
  
  @GetMapping({"wx/questionnaire/getQuestionAndStatus/ing"})
  public ResultEntity getQuestionAndStatus2() {
    return ResultEntity.successWithData(this.questionnaireService.getQuestionAndStatus2());
  }
  
  @GetMapping({"wx/questionnaire/boolean"})
  public boolean getAuthority(@RequestParam("questionnaireName") String questionnaireName, @RequestParam("open_id") String open_id, @RequestParam("identity") String identity) {
    Integer teacherId = this.teacherService.getTeacherId(Base64Utils.decode(open_id));
    Integer studentId = this.studentService.getStudentId(Base64Utils.decode(open_id));
    if (studentId == null && teacherId == null)
      return true; 
    return false;
  }
  
  @GetMapping({"api/wx/questionnaire/getQuestionAndStatus/ing"})
  public ResultEntity getQuestionAndStatus3() {
    return ResultEntity.successWithData(this.questionnaireService.getQuestionAndStatus2());
  }
}
