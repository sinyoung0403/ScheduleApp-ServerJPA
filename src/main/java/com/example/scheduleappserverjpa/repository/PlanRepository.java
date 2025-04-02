package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public interface PlanRepository extends JpaRepository<Plan, Long> {
  default Plan findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(() -> new DataNotFoundException(id + ", 해당 게시글이 존재하지 않습니다."));
  }

  // 유저의 Id 와 일치 하는가
  boolean existsByIdAndUser_Id(Long planId, Long userId);

  // 페이징
  Page<Plan> findAll(Pageable pageable);
}
