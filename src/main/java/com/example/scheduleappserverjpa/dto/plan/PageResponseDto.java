package com.example.scheduleappserverjpa.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto {
  private List<PlanWithUserAndCommentDto> plans;
  private int pageNumber;
  private int pageSize;
  private int totalPages;
  private long totalElements;

  // from 코드
  public static PageResponseDto from(List<PlanWithUserAndCommentDto> plans, int pageNumber, int pageSize, int totalPages, long totalElements) {
    return new PageResponseDto(plans, pageNumber+1, pageSize, totalPages, totalElements);
  }
}
