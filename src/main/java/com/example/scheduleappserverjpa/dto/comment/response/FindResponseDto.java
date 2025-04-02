package com.example.scheduleappserverjpa.dto.comment.response;

import com.example.scheduleappserverjpa.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FindResponseDto {
  private String username;

  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  public static FindResponseDto from(Comment comment) {
    return new FindResponseDto(comment.getUser().getName(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt());
  }
}
