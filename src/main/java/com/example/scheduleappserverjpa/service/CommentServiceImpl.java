package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.comment.request.CreateRequestDto;
import com.example.scheduleappserverjpa.dto.comment.response.CreateResponseDto;
import com.example.scheduleappserverjpa.dto.comment.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.comment.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.entity.Comment;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.repository.CommentRepository;
import com.example.scheduleappserverjpa.repository.PlanRepository;
import com.example.scheduleappserverjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final PlanRepository planRepository;

  // 댓글 생성
  @Override
  public CreateResponseDto createComment(Long planId, Long userId, CreateRequestDto dto) {
    // 데이터 검증 및 조회
    Plan findPlan = planRepository.findByIdOrElseThrow(planId);
    User findUser = userRepository.findByIdOrElseThrow(userId);

    // DTO 를 Entity 로 변환
    Comment comment = new Comment(dto);

    // 연관관계 주입
    comment.setPlan(findPlan);
    comment.setUser(findUser);

    // 댓글 저장
    Comment saved = commentRepository.save(comment);

    // Dto 로 변환 후 반환
    return CreateResponseDto.from(saved);
  }

  // 일정의 댓글 전체 조회
  @Override
  public List<FindResponseDto> findAllByPlanId(Long planId) {
    // 데이터 검증
    planRepository.validateExistenceById(planId);

    // Plan ID 가 일치하는 댓글 조회
    List<Comment> commentList = commentRepository.findByPlan_Id(planId);

    // DTO 로 변환 후 반환
    return commentList.stream()
            .map(FindResponseDto::from)
            .toList();
  }

  // 일정의 댓글 단건 조회
  @Override
  public FindResponseDto findById(Long planId, Long commentId) {
    // 데이터 검증
    planRepository.validateExistenceById(planId);

    // Comment ID 가 일치하는 댓글 조회
    Comment comment = commentRepository.findByIdAndPlan_IdOrElseThrow(commentId, planId);

    // DTO 로 변환 후 반환
    return FindResponseDto.from(comment);
  }

  // 댓글 수정
  @Transactional // 확장성 고려
  @Override
  public void updateComment(Long planId, Long userId, Long commentId, UpdateRequestDto dto) {
    // 데이터 검증
    verifyAllExistOrThrow(planId, userId, commentId);

    // 로그인된 유저의 댓글 수정 접근 검증 및 조회
    Comment comment = commentRepository.findByPlan_IdAndUser_IdAndIdOrElseThrow(planId, userId, commentId);

    // 댓글 업데이트
    comment.updateContent(dto.getContent());
  }

  // 댓글 삭제
  @Transactional
  @Override
  public void deleteComment(Long planId, Long userId, Long commentId) {
    // 데이터 검증
    verifyAllExistOrThrow(planId, userId, commentId);

    // 로그인된 유저의 댓글 삭제 접근 검증 및 조회
    Comment comment = commentRepository.findByPlan_IdAndUser_IdAndIdOrElseThrow(planId, userId, commentId);

    // 댓글 삭제
    commentRepository.delete(comment);
  }

  private void verifyAllExistOrThrow(Long planId, Long userId, Long commentId) {
    // 일정 데이터 검증
    planRepository.validateExistenceById(planId);
    commentRepository.validateExistenceByPlan_Id(planId);

    // 유저 데이터 검증
    userRepository.existsById(userId);

    // 댓글 데이터 검증
    commentRepository.validateExistenceById(commentId);
  }
}
