package com.example.scheduleappserverjpa.dto.plan.response;

import com.example.scheduleappserverjpa.dto.plan.PlanWithUserAndCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class PageResponseDto {
  private List<PlanWithUserAndCommentDto> plans;
  private Map<String, Object> pageInfo;

  // ResponseDto 로 변환
  public static PageResponseDto from(List<PlanWithUserAndCommentDto> plans, int pageNumber, int pageSize, int totalPages, long totalElements) {
    Map<String, Object> pageInfo = new LinkedHashMap<>();
    pageInfo.put("pageNumber", pageNumber + 1);
    pageInfo.put("pageSize", pageSize);
    pageInfo.put("totalPages", totalPages);
    pageInfo.put("totalElements", totalElements);
    return new PageResponseDto(plans, pageInfo);
  }
}
