package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.dto.plan.request.DeleteRequestDto;
import com.example.scheduleappserverjpa.dto.plan.request.SaveRequestDto;
import com.example.scheduleappserverjpa.dto.plan.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.plan.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.plan.response.PageResponseDto;
import com.example.scheduleappserverjpa.dto.plan.response.SaveResponseDto;
import com.example.scheduleappserverjpa.dto.user.LoginDto;
import com.example.scheduleappserverjpa.service.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

  private final PlanService planService;

  /* 일정 추가 */
  @PostMapping
  public ResponseEntity<SaveResponseDto> savePlan(
          @Valid @RequestBody SaveRequestDto dto,
          HttpServletRequest request) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    SaveResponseDto responseDto = planService.savePlan(dto, loginUser.getId());
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  /* 일정 전체 조회 */
  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findByAll() {
    List<FindResponseDto> findResponseDto = planService.findByAll();
    return new ResponseEntity<>(findResponseDto, HttpStatus.OK);
  }

  /* 일정 단건 조회 */
  @GetMapping("/{id}")
  public ResponseEntity<FindResponseDto> findById(@PathVariable Long id) {
    FindResponseDto findResponseDto = planService.findById(id);
    return new ResponseEntity<>(findResponseDto, HttpStatus.OK);
  }

  /* 일정 수정 */
  @PatchMapping("/{id}")
  public ResponseEntity<String> updatePlan(
          @PathVariable Long id,
          @Valid @RequestBody UpdateRequestDto dto,
          HttpServletRequest request
  ) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    planService.updatePlan(id, loginUser.getId(), dto);
    return ResponseEntity.ok("일정 수정이 완료되었습니다.");
  }

  /* 일정 삭제 */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePlan(@PathVariable Long id, @RequestBody DeleteRequestDto dto, HttpServletRequest request) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    planService.deletePlan(id, loginUser.getId(), dto);
    return ResponseEntity.ok("일정 삭제가 완료되었습니다.");
  }

  /* 페이징 조회 */
  @GetMapping("/pages")
  public ResponseEntity<PageResponseDto> page(
          @RequestParam(required = false, defaultValue = "1")
          @Min(value = 1, message = "1 이상의 값을 가져야 합니다.")
          int pageNumber,
          @RequestParam(required = false, defaultValue = "10")
          @Min(value = 1, message = "1 이상의 값을 가져야 합니다.")
          int pageSize
  ) {
    PageResponseDto page = planService.page(pageNumber - 1, pageSize);
    return ResponseEntity.ok(page);
  }
}

