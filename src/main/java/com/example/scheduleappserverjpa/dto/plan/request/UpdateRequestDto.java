package com.example.scheduleappserverjpa.dto.plan.request;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {

  @Size(max = 10)
  private String title;

  private String contents;

  // 둘 다 있는지 유효값 검증
  public boolean isValid() {
    return !StringUtils.isEmpty(title) || !StringUtils.isEmpty(contents);
  }
}
