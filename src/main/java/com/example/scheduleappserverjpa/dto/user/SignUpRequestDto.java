package com.example.scheduleappserverjpa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
  private String name;

  private String email;

  private String pwd;
}
