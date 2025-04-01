package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface PlanRepository extends JpaRepository<Plan, Long> {
  default Plan findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(()->new DataNotFoundException(id + ", 해당 게시글이 존재하지 않습니다."));
  }
}
