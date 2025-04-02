package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.Comment;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import com.example.scheduleappserverjpa.exception.UnauthorizedAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  /* plan id 가 일치하는 모든 댓글 */
  List<Comment> findByPlan_Id(Long planId);

  /* Id 와 Plan Id 가 일치 */
  Optional<Comment> findByIdAndPlan_Id(Long id, Long planId);

  default Comment findByIdAndPlan_IdOrElseThrow(Long commentId, Long planId) {
    return findByIdAndPlan_Id(commentId, planId).orElseThrow(() -> new DataNotFoundException("해당하는 댓글이 존재하지 않습니다."));
  }

  /* Plan 이 존재하는지 확인 */
  boolean existsByPlan_Id(Long planId);

  default void validateExistenceByPlan_Id(Long planId) {
    if (!existsByPlan_Id(planId)) {
      throw new DataNotFoundException("댓글이 존재하지 않는 일정입니다.");
    }
  }

  /* 댓글이 존재하지 않을 경우 */
  default void validateExistenceById(Long id) {
    if (!existsById(id)) {
      throw new DataNotFoundException("해당 댓글이 존재하지 않습니다.");
    }
  }

  /* Comment ID 와 Plan ID, User ID 가 모두 일치 (Long planId, Long userId, Long commentId) */
  Optional<Comment> findByPlan_IdAndUser_IdAndId(Long planId, Long userId, Long commentId);

  default Comment findByPlan_IdAndUser_IdAndIdOrElseThrow(Long planId, Long userId, Long commentId) {
    return findByPlan_IdAndUser_IdAndId(planId, userId, commentId).orElseThrow(() -> new UnauthorizedAccessException("댓글에 접근할 권한이 없습니다."));
  }

  /* 댓글이 적힌 Plan count :: 페이징 조회 시, 일정 별로 댓글 수를 카운팅 해주는 것 */
  long countByPlan_Id(Long planId);
}
