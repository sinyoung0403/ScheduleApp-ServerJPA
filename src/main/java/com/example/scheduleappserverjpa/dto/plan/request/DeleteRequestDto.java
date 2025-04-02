package com.example.scheduleappserverjpa.dto.plan.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRequestDto {
  // 비밀번호
  @NotBlank(message = "비밀번호는 필수값 입니다.")
  private final String pwd;
}
