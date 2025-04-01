package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.comment.CreateRequestDto;
import com.example.scheduleappserverjpa.dto.comment.CreateResponseDto;
import com.example.scheduleappserverjpa.dto.comment.FindResponseDto;
import com.example.scheduleappserverjpa.dto.comment.UpdateRequestDto;

import java.util.List;

public interface CommentService {
  CreateResponseDto createComment(Long planId, Long userId, CreateRequestDto dto);

  List<FindResponseDto> findAllByPlanId(Long planId);

  FindResponseDto findById(Long planId, Long commentId);

  void updateComment(Long planId, Long id, Long commentId, UpdateRequestDto dto);

  void deleteComment(Long planId, Long userId, Long commentId);
}
