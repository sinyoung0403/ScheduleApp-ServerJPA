package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.dto.plan.FindResponseDto;
import com.example.scheduleappserverjpa.dto.plan.SaveRequestDto;
import com.example.scheduleappserverjpa.dto.plan.SaveResponseDto;
import com.example.scheduleappserverjpa.dto.plan.UpdateRequestDto;
import com.example.scheduleappserverjpa.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor // 의존성 주입 시, 생성자 주입 방식을 어노테이션으로 대신
public class PlanController {

  private final PlanService planService;

  // 일정 추가
  @PostMapping
  public ResponseEntity<SaveResponseDto> savePlan(@Valid @RequestBody SaveRequestDto dto) {
    SaveResponseDto responseDto = planService.savePlan(dto.getUsername(), dto.getTitle(), dto.getContents());
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  // 일정 전체 조회
  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findByAll() {
    List<FindResponseDto> findResponseDto = planService.findByAll();
    return new ResponseEntity<>(findResponseDto, HttpStatus.OK);
  }

  // 일정 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<FindResponseDto> findById(@PathVariable Long id) {
    FindResponseDto findResponseDto = planService.findById(id);
    return new ResponseEntity<>(findResponseDto, HttpStatus.OK);
  }

  // 일정 수정
  @PatchMapping("/{id}")
  public ResponseEntity<Void> updatePlan(
          @PathVariable Long id,
          @Valid @RequestBody UpdateRequestDto dto
  ) {
    planService.updatePlan(id, dto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // 일정 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
    planService.deletePlan(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

