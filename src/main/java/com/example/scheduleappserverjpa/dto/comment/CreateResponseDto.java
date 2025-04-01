package com.example.scheduleappserverjpa.dto.comment;

import com.example.scheduleappserverjpa.dto.plan.SaveResponseDto;
import com.example.scheduleappserverjpa.entity.Comment;
import com.example.scheduleappserverjpa.entity.Plan;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateResponseDto {
  // 글을 작성한 유저의 이름
  private String username;

  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  public static CreateResponseDto from(Comment comment) {
    return new CreateResponseDto(
            comment.getUser().getName(),
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
    );
  }
}
