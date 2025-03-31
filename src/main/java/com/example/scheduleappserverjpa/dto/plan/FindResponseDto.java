package com.example.scheduleappserverjpa.dto.plan;

import com.example.scheduleappserverjpa.entity.Plan;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FindResponseDto {
  private String username;

  private String title;

  private String contents;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  public static FindResponseDto from(Plan plan) {
    return new FindResponseDto(plan.getUser().getName(), plan.getTitle(), plan.getContents(), plan.getCreatedAt(), plan.getUpdatedAt());
  }
}
