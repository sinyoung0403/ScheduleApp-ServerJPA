package com.example.scheduleappserverjpa.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRequestDto {
  @NotBlank(message = "댓글은 필수값 입니다.")
  @Size(max = 100)
  private String content;
}
