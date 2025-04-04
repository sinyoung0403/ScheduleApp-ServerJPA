package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.dto.comment.request.CreateRequestDto;
import com.example.scheduleappserverjpa.dto.comment.request.DeleteRequestDto;
import com.example.scheduleappserverjpa.dto.comment.response.CreateResponseDto;
import com.example.scheduleappserverjpa.dto.comment.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.comment.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.user.LoginDto;
import com.example.scheduleappserverjpa.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/plans/{planId}/comments")
public class CommentController {

  private final CommentService commentService;

  /* 댓글 생성  */
  @PostMapping
  public ResponseEntity<CreateResponseDto> createComment(
          @Positive(message = "양수만 허용합니다.") @PathVariable Long planId,
          @Valid @RequestBody CreateRequestDto dto,
          @SessionAttribute(name = "loginUser") LoginDto loginDto
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentService.createComment(planId, loginDto.getId(), dto));
  }

  /* 일정의 댓글 전체 조회 */
  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findAllByPlanId(
          @Positive(message = "양수만 허용합니다.") @PathVariable Long planId) {
    return ResponseEntity.ok(commentService.findAllByPlanId(planId));
  }

  /* 일정의 댓글 단건 조회 */
  @GetMapping("/{commentId}")
  public ResponseEntity<FindResponseDto> findById(
          @Positive(message = "양수만 허용합니다.") @PathVariable Long planId,
          @Positive(message = "양수만 허용합니다.") @PathVariable Long commentId
  ) {
    return ResponseEntity.ok(commentService.findById(planId, commentId));
  }

  /* 댓글 수정 */
  @PatchMapping("/{commentId}")
  public ResponseEntity<String> updateComment(
          @Positive(message = "양수만 허용합니다.") @PathVariable Long planId,
          @Positive(message = "양수만 허용합니다.") @PathVariable Long commentId,
          @SessionAttribute(name = "loginUser") LoginDto loginDto,
          @Valid @RequestBody UpdateRequestDto dto
  ) {
    commentService.updateComment(planId, loginDto.getId(), commentId, dto);
    return ResponseEntity.ok("댓글 수정이 완료되었습니다.");
  }


  /* 댓글 삭제 */
  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deleteComment(
          @Positive(message = "양수만 허용합니다.") @PathVariable Long planId,
          @Positive(message = "양수만 허용합니다.") @PathVariable Long commentId,
          @SessionAttribute(name = "loginUser") LoginDto loginDto,
          @Valid @RequestBody DeleteRequestDto dto
  ) {
    commentService.deleteComment(planId, loginDto.getId(), commentId, dto);
    return ResponseEntity.ok("댓글 삭제가 완료되었습니다.");
  }
}