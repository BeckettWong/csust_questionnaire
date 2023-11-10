package com.example.satisfaction_questionnaire.utils;
public class ResultEntity<T> {
  public static final String SUCCESS = "SUCCESS";
  
  public static final String FAILED = "FAILED";
  
  private String result;
  
  private String message;
  
  private T data;
  
  public static <Type> com.example.satisfaction_questionnaire.utils.ResultEntity<Type> successWithoutData() {
    return new com.example.satisfaction_questionnaire.utils.ResultEntity<>("SUCCESS", null, null);
  }
  
  public static <Type> com.example.satisfaction_questionnaire.utils.ResultEntity<Type> successWithData(Type data) {
    return new com.example.satisfaction_questionnaire.utils.ResultEntity<>("SUCCESS", null, data);
  }
  
  public static <Type> com.example.satisfaction_questionnaire.utils.ResultEntity<Type> failed(String message) {
    return new com.example.satisfaction_questionnaire.utils.ResultEntity<>("FAILED", message, null);
  }
  
  public ResultEntity() {}
  
  public ResultEntity(String result, String message, T data) {
    this.result = result;
    this.message = message;
    this.data = data;
  }
  
  public String toString() {
    return "ResultEntity [result=" + this.result + ", message=" + this.message + ", data=" + this.data + "]";
  }
  
  public String getResult() {
    return this.result;
  }
  
  public void setResult(String result) {
    this.result = result;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public T getData() {
    return this.data;
  }
  
  public void setData(T data) {
    this.data = data;
  }
}
