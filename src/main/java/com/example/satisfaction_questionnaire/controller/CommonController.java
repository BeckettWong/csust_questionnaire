package com.example.satisfaction_questionnaire.controller;

import com.example.satisfaction_questionnaire.entity.Describe;
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
import com.example.satisfaction_questionnaire.vo.ResultVo;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
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

  @PostMapping({"questionnaire/create"})
  public ResultEntity CreateQuestionnaire(@RequestBody QuestionnaireVo questionNaireVo) {
    String questionnaireName = questionNaireVo.getQuestionnaireName();
    String[] surveyName = questionNaireVo.getSurveyName();
    int nums = surveyName.length;
    String[][] questionOption = questionNaireVo.getQuestionOption();
    String questionnaireTarget = questionNaireVo.getQuestionnaireTarget();
    String description = questionNaireVo.getDescription();
    Describe describe = new Describe();
    describe.setQuestionnaireName(questionnaireName);
    describe.setDescription(description);
    describe.setQuestionnaireTarget(questionnaireTarget);
    describeService.addDescribe(describe);
    for (int i = 0; i < nums; i++) {
      surveyService.addSurvey(surveyName[i], questionnaireName);
      for (int j = 0; j < (questionOption[i]).length; j++) {
        String oldStr = questionOption[i][j];
        String[] strs1 = oldStr.split("type:");
        String strQuestion = strs1[0].substring(9);
        Integer integer = surveyService.getSurveyIdByName(surveyName[i], questionnaireName);
        String[] strs2 = strs1[1].split("option:");
        String strType = strs2[0];
        questionService.addQuestion(integer, strType, strQuestion);
        if (!strType.equals("填空")) {
          String[] option = strs2[1].split("&&");
          int questionId = questionService.getQuestionIdByDescription(strQuestion, integer).intValue();
          optionService.addOption(Integer.valueOf(questionId), option);
        } else {
          int questionId = questionService.getQuestionIdByDescription(strQuestion, integer).intValue();
          optionService.addOptionOnly(Integer.valueOf(questionId), " ");
        }
      }
    }
    Integer[] surveyId = surveyService.getSurveyIdBynames(surveyName, questionnaireName);
    for (Integer surveyid : surveyId)
      questionnaireService.addQuestionnaire(questionnaireName, surveyid, questionnaireTarget);
    return ResultEntity.successWithoutData();
  }

  @PostMapping({"questionnaire/upload"})
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
      if (teacherService.getTeacherId(open_id) == null) {
        teacherService.addTeacherInfo(teacher);
        Integer[] surveyID = questionnaireService.getSurveyIdByName(questionnaireName);
        for (int i = 0; i < surveyID.length; i++) {
          Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyID[i]);
          for (int j = 0; j < questionId.length; j++) {
            String[] oldstr = questionAnswer[i][j].split("answer:");
            String type = oldstr[0].substring(5);
            if (type.equals("单选")) {
              Integer optionId = optionService.getOptionId(questionId[j], oldstr[1]);
              Integer userId = teacherService.getTeacherIdByOpenIdAndName(open_id, questionnaireName);
              relationService.addRelation(userId, optionId, "teacher");
            } else if (type.equals("多选")) {
              String[] newStr = oldstr[1].split("&&");
              for (String s : newStr) {
                Integer optionId = optionService.getOptionId(questionId[j], s);
                Integer userId = teacherService.getTeacherIdByOpenIdAndName(open_id, questionnaireName);
                relationService.addRelation(userId, optionId, "teacher");
              }
            } else if (type.equals("填空")) {
              Integer optionId = optionService.getOptionId(questionId[j], " ");
              Integer userId = teacherService.getTeacherIdByOpenIdAndName(open_id, questionnaireName);
              relationService.addRelation2(userId, optionId, oldstr[1], "teacher");
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
      if (studentService.getStudentId(open_id) == null) {
        studentService.addStudentInfo(student);
        Integer[] surveyID = questionnaireService.getSurveyIdByName(questionnaireName);
        for (int i = 0; i < surveyID.length; i++) {
          Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyID[i]);
          for (int j = 0; j < questionId.length; j++) {
            String[] oldstr = questionAnswer[i][j].split("answer:");
            String type = oldstr[0].substring(5);
            if (type.equals("单选")) {
              Integer optionId = optionService.getOptionId(questionId[j], oldstr[1]);
              Integer userId = studentService.getStudentIdByOpenIdAndName(open_id, questionnaireName);
              relationService.addRelation(userId, optionId, "student");
            } else if (type.equals("多选")) {
              String[] newStr = oldstr[1].split("&&");
              for (String s : newStr) {
                Integer optionId1 = optionService.getOptionId(questionId[j], s);
                Integer userId = studentService.getStudentIdByOpenIdAndName(open_id, questionnaireName);
                relationService.addRelation(userId, optionId1, "student");
              }
            } else if (type.equals("填空")) {
              Integer optionId = optionService.getOptionId(questionId[j], " ");
              Integer userId = studentService.getStudentIdByOpenIdAndName(open_id, questionnaireName);
              relationService.addRelation2(userId, optionId, oldstr[1], "student");
            }
          }
        }
      }
    }
    return ResultEntity.successWithoutData();
  }

  @GetMapping("questionnaire/get")
  public ResultEntity GetQuestionnaire(String questionnaireName) {
    QuestionnaireVo questionnaireVo = new QuestionnaireVo();
    questionnaireVo.setQuestionnaireName(questionnaireName);
    String questionnairesTarget = questionnaireService.getQuestionnairesTargetByName(questionnaireName);
    questionnaireVo.setQuestionnaireTarget(questionnairesTarget);
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    questionnaireVo.setSurveyName(surveyName);
    String[][] questionOption = new String[surveyId.length][10];
    questionnaireVo.setDescription(describeService.getDescribe(questionnaireName, questionnairesTarget));
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        StringBuffer sb = new StringBuffer();
        sb.append("question:");
        sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
        sb.append("type:");
        String type = questionService.getQuestionTypeByQuestionId(questionId[j]);
        sb.append(type);
        if (!type.equals("填空")) {
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
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

  @GetMapping("questionnaire/result/all")
  public ResultEntity GetResult(String questionnaireName) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getNum(temp);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
    }
    resultVo.setResultNum(questionResult);
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping("questionnaire/result/teacher")
  public ResultEntity GetTeacherResult(String questionnaireName) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getTeacherNum(temp);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
    }
    resultVo.setResultNum(questionResult);
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping("questionnaire/result/student")
  public ResultEntity GetStudentResult(String questionnaireName) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getStudentNum(temp);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
    }
    resultVo.setResultNum(questionResult);
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping("questionnaire/result/academy")
  public ResultEntity GetAcademyResult(String questionnaireName, String academyName) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getAcademyNum(temp, academyName);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
      resultVo.setResultNum(questionResult);
    }
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping("questionnaire/result/grade")
  public ResultEntity GetGradeResult(String questionnaireName, String grade) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getGradeNum(temp, grade, questionnaireName);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
      resultVo.setResultNum(questionResult);
    }
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping({"questionnaire/result/sort"})
  public ResultEntity GetSortResult(String questionnaireName, String sort) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getSortNum(temp, sort, questionnaireName);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
      resultVo.setResultNum(questionResult);
    }
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping({"questionnaire/result/sex"})
  public ResultEntity GetSexResult(String questionnaireName, String sex) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getSexNum(temp, sex, questionnaireName);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
      resultVo.setResultNum(questionResult);
    }
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping({"questionnaire/result/campus"})
  public ResultEntity GetCampusResult(String questionnaireName, String campus) {
    Integer[] surveyId = questionnaireService.getSurveyIdByName(questionnaireName);
    ResultVo resultVo = new ResultVo();
    resultVo.setQuestionnaireName(questionnaireName);
    String[] surveyName = surveyService.getSurveyNameById(surveyId);
    resultVo.setSurveyName(surveyName);
    String[][] questionResult = new String[surveyId.length][10];
    for (int i = 0; i < surveyId.length; i++) {
      Integer[] questionId = questionService.getQuestionIdBySurveyId(surveyId[i]);
      for (int j = 0; j < questionId.length; j++) {
        if (!questionService.getQuestionTypeByQuestionId(questionId[j]).equals("填空")) {
          StringBuffer sb = new StringBuffer();
          sb.append("question:");
          sb.append(questionService.getQuestionDescriptionByQuestionId(questionId[j]));
          sb.append("type:");
          sb.append(questionService.getQuestionTypeByQuestionId(questionId[j]));
          sb.append("option:");
          String[] option = optionService.getOptionNameByQuestionId(questionId[j]);
          for (int s = 0; s < option.length; s++) {
            sb.append(option[s]);
            if (s == option.length - 1)
              break;
            sb.append("&&");
          }
          sb.append("result:");
          for (int x = 0; x < option.length; x++) {
            String name = option[x];
            Integer temp = optionService.getOptionId(questionId[j], name);
            Integer num = relationService.getCampus(temp, campus, questionnaireName);
            sb.append(num);
            if (x < option.length - 1)
              sb.append("**");
          }
          questionResult[i][j] = sb.toString();
        }
      }
      resultVo.setResultNum(questionResult);
    }
    return ResultEntity.successWithData(resultVo);
  }

  @GetMapping({"questionnaire/getQuestionAndStatus"})
  public ResultEntity getQuestionAndStatus() {
    return ResultEntity.successWithData(questionnaireService.getQuestionAndStatus());
  }

  @GetMapping({"questionnaire/getQuestionAndStatus/ing"})
  public ResultEntity getQuestionAndStatus2() {
    return ResultEntity.successWithData(questionnaireService.getQuestionAndStatus2());
  }

  @PostMapping({"questionnaire/getQuestionAndStatus/update"})
  public ResultEntity updateQuestionAndStatus(String questionnaireName, String questionnaireStatus) {
    return ResultEntity.successWithData(Boolean.valueOf(questionnaireService.updateQuestionAndStatus(questionnaireName, questionnaireStatus)));
  }
}
