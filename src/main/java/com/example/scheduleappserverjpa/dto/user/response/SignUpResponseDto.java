package com.example.scheduleappserverjpa.dto.user.response;

import com.example.scheduleappserverjpa.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {
  private String name;

  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  // ResponseDto 로 변환
  public static SignUpResponseDto from(User user){
    return new SignUpResponseDto(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
  }
}


