package com.example.scheduleappserverjpa.dto.plan.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveRequestDto {
  @NotBlank(message = "제목은 필수값 입니다.")
  @Size(max=10)
  private String title;

  @NotBlank(message = "할일은 필수값 입니다.")
  private String contents;

}
