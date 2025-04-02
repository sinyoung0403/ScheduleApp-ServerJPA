package com.example.scheduleappserverjpa.handler;

import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import com.example.scheduleappserverjpa.exception.InvalidPasswordException;
import com.example.scheduleappserverjpa.exception.InvalidRequestException;
import com.example.scheduleappserverjpa.exception.UnauthorizedAccessException;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  // 파라미터가 없을 경우
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<List<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorToList(ex, "BAD_REQUEST"));
  }

  // 데이터 조회가 불가할 경우
  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<List<String>> handleDataNotFoundException(DataNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorToList(ex, "NOT_FOUND"));
  }

  // 비밀번호가 일치하지 않을 경우
  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<List<String>> handleInvalidPasswordException(InvalidPasswordException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorToList(ex, "UNAUTHORIZED"));
  }

  // 권한이 없는 경우
  @ExceptionHandler(UnauthorizedAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<List<String>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorToList(ex, "UNAUTHORIZED"));
  }

  // Valid 일치하지 않을 경우
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  // PathVariable, Param 검증 에러
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  // 입력값이 이상할 경우
  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<List<String>> handleInvalidInputException(InvalidRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorToList(ex, "BAD_REQUEST"));
  }

  // body 값이 없을 경우
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<List<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorToList(ex, "BAD_REQUEST"));
  }

  // 에러를 리스트에 담아주는 리스트.
  public static List<String> errorToList(Exception exception, String status) {
    List<String> errors = new ArrayList<>();
    errors.add("Message: " + exception.getLocalizedMessage());
    errors.add("ErrorType: " + exception.getClass());
    errors.add("ErrorClass: " + exception.getStackTrace()[0].getClassName());
    errors.add("ErrorMethod: " + exception.getStackTrace()[0].getMethodName());
    errors.add("HttpStatus: " + status);
    return errors;
  }
}
