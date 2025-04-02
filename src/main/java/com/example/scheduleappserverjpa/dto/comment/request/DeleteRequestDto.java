package com.example.scheduleappserverjpa.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRequestDto {
  @NotBlank(message = "비밀번호는 필수값 입니다.")
  private String pwd;
}
