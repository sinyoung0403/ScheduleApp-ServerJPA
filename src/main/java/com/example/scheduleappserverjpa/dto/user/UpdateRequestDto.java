package com.example.scheduleappserverjpa.dto.user;

import lombok.Getter;

@Getter
public class UpdateRequestDto {
  private String name;

  private String email;

  public boolean isValid() {
    return name != null || email != null;
  }
}
