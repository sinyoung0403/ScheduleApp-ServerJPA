package com.example.scheduleappserverjpa.dto.user;

import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {
  private String name;

  private String email;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static SignUpResponseDto from(User user){
    return new SignUpResponseDto(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
  }
}


