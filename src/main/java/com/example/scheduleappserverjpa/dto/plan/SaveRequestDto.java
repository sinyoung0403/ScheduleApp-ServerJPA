package com.example.scheduleappserverjpa.dto.plan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 왜 패키지를 Entity 별로 분리 했냐 ? -> Entity 를 기준으로 기능을 만들기 때문에
// Error 가 있어 수정해야할 일이 있으면 Entity request 와 response 를 동시에 확인해봐야함.
// 그래서 Entity 패키지로 묶어 두면 조금 더 관리하기 쉬울 거 같아서 Entity 를 기준으로 패키지 별로 분리함.

@Getter
@AllArgsConstructor
public class SaveRequestDto {
  @NotBlank(message = "이름은 필수값 입니다.")
  @Size(min=2, max=4)
  private String username;

  @NotBlank(message = "제목은 필수값 입니다.")
  @Size(max=10)
  private String title;

  @NotBlank(message = "할일은 필수값 입니다.")
  private String contents;

}
