package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.Comment;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  // plan id 가 일치하는 모든 댓글.
  List<Comment> findByPlan_Id(Long planId);

  /* Id 와 Plan Id 가 일치 */
  Optional<Comment> findByIdAndPlan_Id(Long id, Long planId);

  // 예외 처리는 Repository 보다는 Service 계층에서 하는게 더 책임 분리 측면에서 더 좋은건가 ?
  // 이런식으로 하는 거보다, 아래처럼 커스텀한 쿼리만 작성하는게 나은건지?
  // 이거는 추후에, 리팩토링을 통해서 한가지 방식으로 선정하는게 나을 거 같다.
  default Comment findByIdAndPlan_IdOrElseThrow(Long commentId, Long planId) {
    return findByIdAndPlan_Id(commentId, planId).orElseThrow(() -> new DataNotFoundException("해당하는 댓글이 존재하지 않습니다."));
  }

  /**/
  boolean existsByPlan_Id(Long planId);

  /* Id 와 Plan Id, User Id 가 모두 일치 (Long planId, Long userId, Long commentId)*/
  Optional<Comment> findByPlan_IdAndUser_IdAndId(Long planId, Long userId, Long commentId);

  long countByPlan_Id(Long planId);
}
