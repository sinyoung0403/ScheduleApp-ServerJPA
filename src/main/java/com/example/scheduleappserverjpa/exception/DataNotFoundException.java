package com.example.scheduleappserverjpa.exception;

public class DataNotFoundException extends RuntimeException{
  public DataNotFoundException(String message) {
    super(message);
  }
}