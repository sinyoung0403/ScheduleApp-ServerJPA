package com.example.scheduleappserverjpa.dto.user.response;

import com.example.scheduleappserverjpa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindResponseDto {
  private Long id;

  private String name;

  private String email;

  // ResponseDto 로 변환
  public static FindResponseDto from(User user) {
    return new FindResponseDto(user.getId(), user.getName(), user.getEmail());
  }
}
