package com.example.scheduleappserverjpa.dto.user;

import com.example.scheduleappserverjpa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDto {
  private final Long id;

  public static LoginDto from(User user) {
    return new LoginDto(user.getId());
  }
}
