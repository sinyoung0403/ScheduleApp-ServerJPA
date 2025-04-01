package com.example.scheduleappserverjpa.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteRequestDto {
  @NotBlank(message = "비밀번호는 필수값 입니다.")
  private String pwd;
}
