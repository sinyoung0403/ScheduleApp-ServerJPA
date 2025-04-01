package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.Comment;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  // plan id 가 일치하는 모든 댓글.
  List<Comment> findByPlan_Id(Long planId);

  /* Id 와 Plan Id 가 일치 */
  Optional<Comment> findByIdAndPlan_Id(Long id, Long planId);

  default Comment findByIdAndPlan_IdOrElseThrow(Long commentId, Long planId) {
    return findByIdAndPlan_Id(commentId, planId).orElseThrow(() -> new DataNotFoundException("해당하는 댓글이 존재하지 않습니다."));
  }

  /**/
  boolean existsByPlan_Id(Long planId);

  /* Id 와 Plan Id, User Id 가 모두 일치 (Long planId, Long userId, Long commentId)*/
  Optional<Comment> findByPlan_IdAndUser_IdAndId(Long planId, Long userId, Long commentId);

  Long plan(Plan plan);
}
