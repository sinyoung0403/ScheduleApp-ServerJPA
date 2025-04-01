package com.example.scheduleappserverjpa.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {
  private final String content;
}
