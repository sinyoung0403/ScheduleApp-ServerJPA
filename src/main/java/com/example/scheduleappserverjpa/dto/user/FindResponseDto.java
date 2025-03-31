package com.example.scheduleappserverjpa.dto.user;

import com.example.scheduleappserverjpa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindResponseDto {
  private Long id;

  private String name;

  private String email;

  // User 를 dto 로 바꾸는 로직
  public static FindResponseDto from(User user) {
    return new FindResponseDto(user.getId(), user.getName(), user.getEmail());
  }
}
