package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import com.example.scheduleappserverjpa.exception.UnauthorizedAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
  /* Plan id 가 일치하는 일정 */
  default Plan findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(() -> new DataNotFoundException(id + ", 해당 일정이 존재하지 않습니다."));
  }

  /* Plan 이 존재하는지 확인 */
  default void validateExistenceById(Long id) {
    if (!existsById(id)) {
      throw new DataNotFoundException("해당 일정이 존재하지 않습니다.");
    }
  }

  /* Plan ID, User ID 가 모두 일치 */
  boolean existsByIdAndUser_Id(Long planId, Long userId);

  default void validatePlanAccess(Long planId, Long userId) {
    if (!existsByIdAndUser_Id(planId, userId)) {
      throw new UnauthorizedAccessException("권한이 없는 일정에 접근 하셨습니다.");
    }
  }

  /* 일정 페이징 조회 */
  Page<Plan> findAll(Pageable pageable);
}
