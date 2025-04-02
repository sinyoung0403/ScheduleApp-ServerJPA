package com.example.scheduleappserverjpa.dto.user.response;

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

  // ResponseDto 로 변환
  public static SignUpResponseDto from(User user){
    return new SignUpResponseDto(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
  }
}


