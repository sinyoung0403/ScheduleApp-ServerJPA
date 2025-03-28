package com.example.scheduleappserverjpa.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {
  // 제목
  private String title;

  // 할일
  private String contents;

  // 둘다 없는지 확인하는 로직
  public boolean isValid() {
    return title != null || contents != null;
  }
}
