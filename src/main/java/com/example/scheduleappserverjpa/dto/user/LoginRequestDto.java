package com.example.scheduleappserverjpa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
  private String email;

  private String pwd;
}
