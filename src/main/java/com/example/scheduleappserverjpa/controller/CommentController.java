package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.dto.comment.CreateRequestDto;
import com.example.scheduleappserverjpa.dto.comment.CreateResponseDto;
import com.example.scheduleappserverjpa.dto.comment.FindResponseDto;
import com.example.scheduleappserverjpa.dto.comment.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.user.LoginDto;
import com.example.scheduleappserverjpa.service.CommentService;
import jakarta.servlet.ServletRequest;
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

  // 댓글 추가
  // 로그인 되어 있는 유저가 생성을 하는 거기 때문에 ?
  // 고민 1.
  // 1. api 관점에서 보면 "/comments/postID" 하는게 나을지 => 상위 객체를 통해서 접근하는게 나을 거 같다.
  // api 는 url 을 자원단위로 설계하는게 좋다고 들었음. Plan 을 통해서만 접근을 한다. 이게 나을 거 같다 !
  // 보통은 ... /plans/{id}/comments 이러한 url 을 따른다고 한다.
  //
  // 2. PostId 를 Dto 에 담아서 보낼지. => 해당 방법은 확장성이 좋은 점이라고 생각하지만,
  //                            dto 에 담아 보내면 controller 만 보고 의미를 바로 파악하기 힘듦.
  // 고민 2.
  // request 를 가져와서 컨트롤러에서 loginDto 로 변환해주고 있는데.
  // 해당 로직이 컨트롤러에 있는게 나을지? => 컨트롤러에 있는 경우라면 요청을 주는 관점에서 맞는 거 같고. 이게 맞는 거다 !
  // 아니면, 비즈니스 로직에 있는게 맞을지 모르겠다. => 이렇게 할 경우 서비스에 request 가 종속되어서 안하는게 맞는 거 같다.
  @PostMapping
  public ResponseEntity<CreateResponseDto> createComment(
          @PathVariable Long planId,
          @RequestBody CreateRequestDto dto,
          HttpServletRequest request) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    // dto 를 그대로 넘길지, 아니면 id 값을 그대로 줄지. dto 를 그대로 보내면 종속되어 버린다.
    // request 는 서비스에 종속되지 않게 하는게 나을 거 같다. 테이블, 유저, dto 순으로
    return new ResponseEntity<>(commentService.createComment(planId, loginUser.getId(), dto), HttpStatus.CREATED);
  }

  // 일정에 있는 댓글 조회
  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findAllByPlanId(
          @PathVariable Long planId) {
    return new ResponseEntity<>(commentService.findAllByPlanId(planId), HttpStatus.OK);
  }

  // 일정 속 댓글 하나의 댓글 조회.
  @GetMapping("/{commentId}")
  public ResponseEntity<FindResponseDto> findById(
          @PathVariable Long planId,
          @PathVariable Long commentId
  ) {
    return new ResponseEntity<>(commentService.findById(planId, commentId), HttpStatus.OK);
  }

  // 댓글 수정 해당 로그인 한 유저와 댓글 쓴 사람이랑 같아야지 수정 가능.
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
