package com.example.scheduleappserverjpa.dto.plan.response;

import com.example.scheduleappserverjpa.entity.Plan;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SaveResponseDto {

  private String username;

  private String title;

  private String contents;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  // ResponseDto 로 변환
  public static SaveResponseDto from(Plan plan) {
    return new SaveResponseDto(
            plan.getUser().getName(),
            plan.getTitle(),
            plan.getContents(),
            plan.getCreatedAt(),
            plan.getUpdatedAt());
  }
}
