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


  // 생성자도 만들어주어야함.
  // 왜냐 ? Entity 를 dto 로 담을려면 entity 를 dto 형태로 변환해주어야하기 때문임
  // 근데 우리는 정적 팩토리 메서드 방식을 선택 할 거임 !
  public static FindResponseDto toDto(Plan plan) {
    return new FindResponseDto(plan.getUserId(), plan.getTitle(), plan.getContents(), plan.getCreatedAt(), plan.getUpdatedAt());
  }
}
