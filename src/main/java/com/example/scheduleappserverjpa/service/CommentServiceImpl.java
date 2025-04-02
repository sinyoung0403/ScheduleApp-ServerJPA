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

  @Override
  public CreateResponseDto createComment(Long planId, Long userId, CreateRequestDto dto) {
    // 먼저 id 에 해당하는 유저를 유저 테이블에서 찾고.
    Plan findPlan = planRepository.findById(planId)
            .orElseThrow(() -> new DataNotFoundException("댓글을 생성하기 이전에, 해당 일정이 존재하지 않습니다."));
    User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new DataNotFoundException("댓글을 생성하기 이전에, 해당 유저가 존재하지 않습니다."));

    // Entity 를 dto 에 맞게 새로 만들고.
    Comment comment = new Comment(dto);

    comment.setPlan(findPlan);
    comment.setUser(findUser);

    Comment saved = commentRepository.save(comment);
    return CreateResponseDto.from(saved);
  }

  @Override
  public List<FindResponseDto> findAllByPlanId(Long planId) {
    // planId 가 존재하지 않을 경우도 고려할 것.
    planRepository.findByIdOrElseThrow(planId);

    // plan id 가 일치하는 경우를 찾기.
    List<Comment> commentList = commentRepository.findByPlan_Id(planId);
    return commentList.stream().map(FindResponseDto::from).toList();
  }

  @Override
  public FindResponseDto findById(Long planId, Long commentId) {
    // 똑같이 존재하지 않을 경우 고려.
    planRepository.findByIdOrElseThrow(planId);
    // 유저 아이디와, id, 댓글 모두 조회
    Comment comment = commentRepository.findByIdAndPlan_IdOrElseThrow(commentId, planId);
    return FindResponseDto.from(comment);
  }

  @Transactional // 확장성 고려
  @Override
  public void updateComment(Long planId, Long userId, Long commentId, UpdateRequestDto dto) {
    // 일정 테이블과 유저 테이블에 해당하는 아이디가 있는지 없는지부터 조회 이거는 따로 빼라네 'ㅡ' ??
    if (!planRepository.existsById(planId) || !commentRepository.existsByPlan_Id(planId)) {
      throw new DataNotFoundException("해당 일정이 존재하지 않습니다.");
    }

    if (!userRepository.existsById(userId)) { // 일어나지는 않을 거임.
      throw new DataNotFoundException("해당 유저가 존재하지 않습니다.");
    }

    if (!commentRepository.existsById(commentId)) {
      throw new DataNotFoundException("해당 댓글이 존재하지 않습니다.");
    }

    Comment comment = commentRepository.findByPlan_IdAndUser_IdAndId(planId, userId, commentId)
            .orElseThrow(() -> new DataNotFoundException("해당 댓글을 수정할 수 없습니다."));

    comment.updateContent(dto.getContent());
  }

  @Transactional // 확장성 고려
  @Override
  public void deleteComment(Long planId, Long userId, Long commentId) {
    if (!planRepository.existsById(planId) || !commentRepository.existsByPlan_Id(planId)) {
      throw new DataNotFoundException("해당 일정이 존재하지 않습니다.");
    }

    if (!userRepository.existsById(userId)) {
      throw new DataNotFoundException("해당 유저가 존재하지 않습니다.");
    }

    if (!commentRepository.existsById(commentId)) {
      throw new DataNotFoundException("해당 댓글이 존재하지 않습니다.");
    }

    Comment comment = commentRepository.findByPlan_IdAndUser_IdAndId(planId, userId, commentId)
            .orElseThrow(() -> new DataNotFoundException("해당 댓글을 삭제할 수 없습니다."));

    commentRepository.delete(comment);
  }
}
