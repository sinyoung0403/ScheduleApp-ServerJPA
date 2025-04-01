package com.example.scheduleappserverjpa.dto.plan;

import com.example.scheduleappserverjpa.entity.Plan;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PlanWithUserAndCommentDto {
  // 일정 작성 유저명
  private final String userName;
  // 할일 제목
  private final String title;
  // 할일 내용
  private final String contents;
  // 댓글 개수
  private final Long commentCount;
  // 일정 작성일 과 수정일
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private final LocalDateTime createdAt;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private final LocalDateTime updatedAt;

  public static PlanWithUserAndCommentDto from(String name, Plan plan, Long commentCount) {
    return new PlanWithUserAndCommentDto(name, plan.getTitle(), plan.getContents(), commentCount, plan.getCreatedAt(), plan.getUpdatedAt());
  }
}
