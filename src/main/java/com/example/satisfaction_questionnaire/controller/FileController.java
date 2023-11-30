package com.example.satisfaction_questionnaire.controller;

import com.example.satisfaction_questionnaire.entity.Relation;
import com.example.satisfaction_questionnaire.entity.Student;
import com.example.satisfaction_questionnaire.entity.Teacher;
import com.example.satisfaction_questionnaire.service.OptionService;
import com.example.satisfaction_questionnaire.service.QuestionService;
import com.example.satisfaction_questionnaire.service.QuestionnaireService;
import com.example.satisfaction_questionnaire.service.RelationService;
import com.example.satisfaction_questionnaire.service.StudentService;
import com.example.satisfaction_questionnaire.service.SurveyService;
import com.example.satisfaction_questionnaire.service.TeacherService;
import com.example.satisfaction_questionnaire.service.UserService;
import com.example.satisfaction_questionnaire.vo.AllVo;
import com.example.satisfaction_questionnaire.vo.StudentVo;
import com.example.satisfaction_questionnaire.vo.TeacherVo;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
  @Autowired
  QuestionnaireService questionnaireService;

  @Autowired
  SurveyService surveyService;

  @Autowired
  RelationService relationService;

  @Autowired
  StudentService studentService;

  @Autowired
  TeacherService teacherService;

  @Autowired
  UserService userService;

  @Autowired
  OptionService optionService;

  @Autowired
  QuestionService questionService;

  @RequestMapping({"questionnaire/studentexcel"})
  @ResponseBody
  public void writeStudentExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("questionnaireName") String questionnaireName) throws Exception {
    List<StudentVo> studentVos = findAllStudentVo(questionnaireName);
    HSSFWorkbook hw = student(studentVos, questionnaireName);
    toExcel(response, hw, "student");
  }

  @RequestMapping({"questionnaire/teacherexcel"})
  @ResponseBody
  public void writeTeacherExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("questionnaireName") String questionnaireName) throws Exception {
    List<TeacherVo> teacherVos = findAllTeacherVo(questionnaireName);
    HSSFWorkbook hssfWorkbook = teacher(teacherVos, questionnaireName);
    toExcel(response, hssfWorkbook, "teacher");
  }

  @RequestMapping({"questionnaire/allexcel"})
  @ResponseBody
  public void writeAllExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("questionnaireName") String questionnaireName) throws Exception {
    List<AllVo> allVos = findAllAllVo(questionnaireName);
    HSSFWorkbook hssfWorkbook = All(allVos, questionnaireName);
    toExcel(response, hssfWorkbook, "all");
  }

  public void setResponseHeader(HttpServletResponse response, String fileName) throws IOException {
    response.setContentType("application/octet-stream");
    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
    response.setHeader("Content-Disposition", "attachment; filename=" +
            URLEncoder.encode(fileName, "UTF-8"));
  }

  public void toExcel(HttpServletResponse response, HSSFWorkbook wk, String name) throws Exception {
    String fileName = name + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xls";
    try {
      setResponseHeader(response, fileName);
      ServletOutputStream servletOutputStream = response.getOutputStream();
      wk.write((OutputStream)servletOutputStream);
      servletOutputStream.flush();
      servletOutputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<StudentVo> findAllStudentVo(String questionnaireName) throws Exception {
    List<Integer> surveyIdByName = Arrays.asList(questionnaireService.getSurveyIdByName(questionnaireName));
    List<Integer> questionIdsByType = questionService.getQuestionIdsByType(surveyIdByName);
    List<StudentVo> studentVos = new ArrayList<>();
    for (Integer questionId : questionIdsByType) {
      Integer optionIdByQuestionId = optionService.getOptionIdByQuestionId(questionId);
      List<Relation> studentOptionContent = relationService.getStudentOptionContent(optionIdByQuestionId);
      for (Relation r : studentOptionContent) {
        StudentVo studentVo = new StudentVo();
        String questionDescriptionByQuestionId = questionService.getQuestionDescriptionByQuestionId(questionId);
        studentVo.setQuestionDescription(questionDescriptionByQuestionId);
        studentVo.setOptionContent(r.getOptionContent());
        Student studentByOpenId = studentService.getStudentByStudentId(r.getUserId());
        studentVo.setAcademy(studentByOpenId.getAcademyName());
        studentVo.setCampus(studentByOpenId.getCampus());
        studentVo.setCreateDate(studentByOpenId.getCreateDate());
        studentVo.setSex(studentByOpenId.getSex());
        studentVo.setGarde(studentByOpenId.getGrade());
        studentVos.add(studentVo);
      }
    }
    return studentVos;
  }

  public List<AllVo> findAllAllVo(String questionnaireName) throws Exception {
    List<Integer> surveyIdByName = Arrays.asList(questionnaireService.getSurveyIdByName(questionnaireName));
    List<Integer> questionIdsByType = questionService.getQuestionIdsByType(surveyIdByName);
    List<StudentVo> studentVos = findAllStudentVo(questionnaireName);
    List<TeacherVo> teacherVos = findAllTeacherVo(questionnaireName);
    List<AllVo> allVos = new ArrayList<>();
    for (StudentVo studentVo : studentVos) {
      AllVo allVo = new AllVo();
      allVo.setCampus(studentVo.getCampus());
      allVo.setSex(studentVo.getSex());
      allVo.setCreateDate(studentVo.getCreateDate());
      allVo.setOptionContent(studentVo.getOptionContent());
      allVo.setQuestionDescription(studentVo.getQuestionDescription());
      allVo.setStatus("学生,年级:" + studentVo.getGarde());
      allVos.add(allVo);
    }
    for (TeacherVo teacherVo : teacherVos) {
      AllVo allVo = new AllVo();
      allVo.setCampus(teacherVo.getCampus());
      allVo.setSex(teacherVo.getSex());
      allVo.setCreateDate(teacherVo.getCreateDate());
      allVo.setOptionContent(teacherVo.getOptionContent());
      allVo.setQuestionDescription(teacherVo.getQuestionDescription());
      allVo.setStatus("老师:" + teacherVo.getSort());
      allVos.add(allVo);
    }
    return allVos;
  }

  public List<TeacherVo> findAllTeacherVo(String questionnaireName) throws Exception {
    List<Integer> surveyIdByName = Arrays.asList(questionnaireService.getSurveyIdByName(questionnaireName));
    List<Integer> questionIdsByType = questionService.getQuestionIdsByType(surveyIdByName);
    List<TeacherVo> teacherVos = new ArrayList<>();
    for (Integer questionId : questionIdsByType) {
      Integer optionIdByQuestionId = optionService.getOptionIdByQuestionId(questionId);
      List<Relation> teacherOptionContent = relationService.getTeacherOptionContent(optionIdByQuestionId);
      for (Relation r : teacherOptionContent) {
        TeacherVo teacherVo = new TeacherVo();
        String questionDescriptionByQuestionId = questionService.getQuestionDescriptionByQuestionId(questionId);
        teacherVo.setQuestionDescription(questionDescriptionByQuestionId);
        teacherVo.setOptionContent(r.getOptionContent());
        Teacher teacherByOpenId = teacherService.getTeacherByTeacherId(r.getUserId());
        teacherVo.setCampus(teacherByOpenId.getCampus());
        teacherVo.setCreateDate(teacherByOpenId.getCreateDate());
        teacherVo.setSex(teacherByOpenId.getSex());
        teacherVo.setSort(teacherByOpenId.getSort());
        teacherVos.add(teacherVo);
      }
    }
    return teacherVos;
  }

  public HSSFWorkbook student(List<StudentVo> studentVos, String questionnaireName) {
    HSSFWorkbook wk = new HSSFWorkbook();
    HSSFSheet sheet = wk.createSheet("问卷名------" + questionnaireName + "--------学生意见建议表");
    HSSFRow smallTitle = sheet.createRow(0);
    smallTitle.createCell(0).setCellValue("校区");
    smallTitle.createCell(1).setCellValue("年级");
    smallTitle.createCell(2).setCellValue("性别");
    smallTitle.createCell(3).setCellValue("学院");
    smallTitle.createCell(4).setCellValue("回答问题的时间");
    smallTitle.createCell(5).setCellValue("问题");
    smallTitle.createCell(6).setCellValue("回答的答案");
    int count = 1;
    for (StudentVo studentVo : studentVos) {
      HSSFRow row = sheet.createRow(count++);
      row.createCell(0).setCellValue(studentVo.getCampus());
      row.createCell(1).setCellValue(studentVo.getGarde());
      row.createCell(2).setCellValue(studentVo.getSex());
      row.createCell(3).setCellValue(studentVo.getAcademy());
      row.createCell(4).setCellValue(studentVo.getCreateDate().toString());
      row.createCell(5).setCellValue(studentVo.getQuestionDescription());
      row.createCell(6).setCellValue(studentVo.getOptionContent());
    }
    return wk;
  }

  public HSSFWorkbook teacher(List<TeacherVo> teacherVos, String questionnaireName) {
    System.out.println("进入HSSFWorkbook===========================");
    for (TeacherVo t : teacherVos)
      System.out.println(t);
    HSSFWorkbook wk = new HSSFWorkbook();
    HSSFSheet sheet = wk.createSheet("问卷名------" + questionnaireName + "--------老师意见建议表");
    HSSFRow smallTitle = sheet.createRow(0);
    smallTitle.createCell(0).setCellValue("校区");
    smallTitle.createCell(1).setCellValue("性别");
    smallTitle.createCell(2).setCellValue("回答问题的时间");
    smallTitle.createCell(3).setCellValue("问题");
    smallTitle.createCell(4).setCellValue("回答的答案");
    smallTitle.createCell(5).setCellValue("岗位");
    int count = 1;
    for (TeacherVo teacherVo : teacherVos) {
      HSSFRow row = sheet.createRow(count++);
      row.createCell(0).setCellValue(teacherVo.getCampus());
      row.createCell(1).setCellValue(teacherVo.getSex());
      row.createCell(2).setCellValue(teacherVo.getCreateDate().toString());
      row.createCell(3).setCellValue(teacherVo.getQuestionDescription());
      row.createCell(4).setCellValue(teacherVo.getOptionContent());
      row.createCell(5).setCellValue(teacherVo.getSort());
    }
    return wk;
  }

  public HSSFWorkbook All(List<AllVo> allVos, String questionnaireName) {
    HSSFWorkbook wk = new HSSFWorkbook();
    HSSFSheet sheet = wk.createSheet("问卷名------" + questionnaireName + "--------所有意见建议表");
    HSSFRow smallTitle = sheet.createRow(0);
    smallTitle.createCell(0).setCellValue("校区");
    smallTitle.createCell(1).setCellValue("性别");
    smallTitle.createCell(2).setCellValue("回答问题的时间");
    smallTitle.createCell(3).setCellValue("问题");
    smallTitle.createCell(4).setCellValue("回答的答案");
    smallTitle.createCell(5).setCellValue("岗位");
    int count = 1;
    for (AllVo allVo : allVos) {
      HSSFRow row = sheet.createRow(count++);
      row.createCell(0).setCellValue(allVo.getCampus());
      row.createCell(1).setCellValue(allVo.getSex());
      row.createCell(2).setCellValue(allVo.getCreateDate().toString());
      row.createCell(3).setCellValue(allVo.getQuestionDescription());
      row.createCell(4).setCellValue(allVo.getOptionContent());
      row.createCell(5).setCellValue(allVo.getStatus());
    }
    return wk;
  }
}
