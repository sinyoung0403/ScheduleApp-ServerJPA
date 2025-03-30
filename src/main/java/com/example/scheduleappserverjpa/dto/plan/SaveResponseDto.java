package com.example.scheduleappserverjpa.dto.plan;

import com.example.scheduleappserverjpa.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SaveResponseDto {
  // 응답을 줄 때는 유저 이름, 제목, 생성일과 수정일을 반환하도록 했네
  private String username;

  private String title;

  private String contents;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  // 생성자도 만들어주어야함.
  // 왜냐 ? Entity 를 dto 로 담을려면 entity 를 dto 형태로 변환해주어야하기 때문임
  public static SaveResponseDto from(Plan plan) {
    return new SaveResponseDto(
            plan.getUser().getName(),
            plan.getTitle(),
            plan.getContents(),
            plan.getCreatedAt(),
            plan.getUpdatedAt());
  }
}
