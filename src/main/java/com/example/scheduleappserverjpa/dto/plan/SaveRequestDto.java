package com.example.scheduleappserverjpa.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 왜 패키지를 Entity 별로 분리 했냐 ? -> Entity 를 기준으로 기능을 만들기 때문에
// Error 가 있어 수정해야할 일이 있으면 Entity request 와 response 를 동시에 확인해봐야함.
// 그래서 Entity 패키지로 묶어 두면 조금 더 관리하기 쉬울 거 같아서 Entity 를 기준으로 패키지 별로 분리함.

@Getter
@AllArgsConstructor
public class SaveRequestDto {
  // 일정을 추가할 때 필요한 것은 유저명, 제목, 내용, 작성일, 수정일이 필요.
  // 하지만 우리는 작성일과 수정일을 자동으로 해줄거기 때문에 생략!

  // 유저명, 타이틀 컨텐츠만 받을 거임. 일단은 생성자나 그런건 필요없는듯 ?
  private String username;
  private String title;
  private String contents;

}
