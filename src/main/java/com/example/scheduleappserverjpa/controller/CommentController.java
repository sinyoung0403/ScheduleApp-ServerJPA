package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.dto.comment.request.CreateRequestDto;
import com.example.scheduleappserverjpa.dto.comment.response.CreateResponseDto;
import com.example.scheduleappserverjpa.dto.comment.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.comment.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.user.LoginDto;
import com.example.scheduleappserverjpa.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plans/{planId}/comments")
public class CommentController {

  private final CommentService commentService;

  // 댓글 생성
  @PostMapping
  public ResponseEntity<CreateResponseDto> createComment(
          @PathVariable Long planId,
          @RequestBody CreateRequestDto dto,
          HttpServletRequest request) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    return new ResponseEntity<>(commentService.createComment(planId, loginUser.getId(), dto), HttpStatus.CREATED);
  }

  // 일정에 있는 댓글 조회
  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findAllByPlanId(
          @PathVariable Long planId) {
    return new ResponseEntity<>(commentService.findAllByPlanId(planId), HttpStatus.OK);
  }

  // 일정 속 하나의 댓글 조회
  @GetMapping("/{commentId}")
  public ResponseEntity<FindResponseDto> findById(
          @PathVariable Long planId,
          @PathVariable Long commentId
  ) {
    return new ResponseEntity<>(commentService.findById(planId, commentId), HttpStatus.OK);
  }

  // 댓글 수정
  @PatchMapping("/{commentId}")
  public ResponseEntity<String> updateComment(
          @PathVariable Long planId,
          @PathVariable Long commentId,
          @RequestBody UpdateRequestDto dto,
          HttpServletRequest request
  ) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    commentService.updateComment(planId, loginUser.getId(), commentId, dto);
    return ResponseEntity.ok("댓글 수정이 완료되었습니다.");
  }


  // 댓글 삭제
  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deleteComment(
          @PathVariable Long planId,
          @PathVariable Long commentId,
          HttpServletRequest request
  ) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    commentService.deleteComment(planId, loginUser.getId(), commentId);
    return ResponseEntity.ok("댓글 삭제가 완료되었습니다.");
  }

}
