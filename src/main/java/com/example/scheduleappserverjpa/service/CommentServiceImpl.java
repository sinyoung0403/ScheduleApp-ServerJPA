package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.comment.request.CreateRequestDto;
import com.example.scheduleappserverjpa.dto.comment.response.CreateResponseDto;
import com.example.scheduleappserverjpa.dto.comment.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.comment.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.entity.Comment;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
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

  // 댓글 추가
  @Override
  public CreateResponseDto createComment(Long planId, Long userId, CreateRequestDto dto) {

    Plan findPlan = planRepository.findByIdOrElseThrow(planId);
    User findUser = userRepository.findByIdOrElseThrow(userId);

    Comment comment = new Comment(dto);

    comment.setPlan(findPlan);
    comment.setUser(findUser);

    Comment saved = commentRepository.save(comment);
    return CreateResponseDto.from(saved);
  }

  // 일정의 댓글 전체 조회
  @Override
  public List<FindResponseDto> findAllByPlanId(Long planId) {

    planRepository.validateExistenceById(planId);

    // plan id 가 일치하는 경우를 찾기.
    List<Comment> commentList = commentRepository.findByPlan_Id(planId);
    return commentList.stream().map(FindResponseDto::from).toList();
  }

  // 일정의 댓글 단건 조회
  @Override
  public FindResponseDto findById(Long planId, Long commentId) {

    planRepository.validateExistenceById(planId);

    // 유저 아이디와, id, 댓글 모두 조회
    Comment comment = commentRepository.findByIdAndPlan_IdOrElseThrow(commentId, planId);
    return FindResponseDto.from(comment);
  }

  // 댓글 수정
  @Transactional // 확장성 고려
  @Override
  public void updateComment(Long planId, Long userId, Long commentId, UpdateRequestDto dto) {

    verifyAllExistOrThrow(planId, userId, commentId);

    Comment comment = commentRepository.findByPlan_IdAndUser_IdAndIdOrElseThrow(planId, userId, commentId);

    comment.updateContent(dto.getContent());
  }

  // 댓글 삭제
  @Transactional
  @Override
  public void deleteComment(Long planId, Long userId, Long commentId) {

    verifyAllExistOrThrow(planId, userId, commentId);

    Comment comment = commentRepository.findByPlan_IdAndUser_IdAndIdOrElseThrow(planId, userId, commentId);

    commentRepository.delete(comment);
  }

  private void verifyAllExistOrThrow(Long planId, Long userId, Long commentId) {
    // 일정 존재 조회
    planRepository.validateExistenceById(planId);
    commentRepository.validateExistenceByPlan_Id(planId);

    // 유저 존재 여부 확인
    userRepository.existsById(userId);

    // 댓글 존재 여부 확인
    commentRepository.validateExistenceById(commentId);
  }
}
