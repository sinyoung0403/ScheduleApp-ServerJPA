package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.config.PasswordEncoder;
import com.example.scheduleappserverjpa.dto.plan.*;
import com.example.scheduleappserverjpa.dto.plan.request.DeleteRequestDto;
import com.example.scheduleappserverjpa.dto.plan.request.SaveRequestDto;
import com.example.scheduleappserverjpa.dto.plan.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.plan.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.plan.response.PageResponseDto;
import com.example.scheduleappserverjpa.dto.plan.response.SaveResponseDto;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.InvalidPasswordException;
import com.example.scheduleappserverjpa.exception.InvalidRequestException;
import com.example.scheduleappserverjpa.exception.UnauthorizedAccessException;
import com.example.scheduleappserverjpa.repository.CommentRepository;
import com.example.scheduleappserverjpa.repository.PlanRepository;
import com.example.scheduleappserverjpa.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.scheduleappserverjpa.dto.plan.response.FindResponseDto.from;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final PasswordEncoder passwordEncoder;

  // 일정 추가
  @Override
  public SaveResponseDto savePlan(SaveRequestDto dto, Long loginId) {
    // 로그인한 유저 데이터 검증 및 조회
    User findUser = userRepository.findByIdOrElseThrow(loginId);

    // Dto 를 Entity 변환
    Plan plan = new Plan(dto.getTitle(), dto.getContents());

    // 연관관계 설정
    plan.setUser(findUser);

    // 일정 저장
    Plan saved = planRepository.save(plan);

    // DTO 로 변환 후 반환
    return SaveResponseDto.from(saved);
  }

  // 일정 전체 조회
  @Override
  public List<FindResponseDto> findByAll() {
    return planRepository.findAll().stream()
            .map(FindResponseDto::from)
            .toList();
  }

  // 일정 단건 조회
  @Override
  public FindResponseDto findById(Long id) {
    // 데이터 검증 및 조회
    Plan findPlan = planRepository.findByIdOrElseThrow(id);

    // DTO 로 변환 후 반환
    return from(findPlan);
  }

  // 일정 수정
  @Transactional
  @Override
  public void updatePlan(Long planId, Long userId, UpdateRequestDto dto) {
    // DTO 데이터 검증
    if (!dto.isValid()) {
      throw new InvalidRequestException("입력 값이 유효하지 않습니다. 둘 중 하나의 값은 입력해야 합니다.");
    }

    // 데이터 검증 및 조회
    Plan findPlan = planRepository.findByIdOrElseThrow(planId);

    // 로그인된 User ID 와 접근하는 Plan 의 User ID 일치 여부 검증
    planRepository.validatePlanAccess(planId, userId);

    // 기존 값을 유지하거나 입력된 값으로 변경
    String setTitle = (StringUtils.isEmpty(dto.getTitle())) ? findPlan.getTitle() : dto.getTitle();
    String setContents = (StringUtils.isEmpty(dto.getContents())) ? findPlan.getContents() : dto.getContents();

    // 일정 업데이트
    findPlan.updateTitle(setTitle);
    findPlan.updateContents(setContents);
  }

  // 일정 삭제
  @Override
  public void deletePlan(Long planId, Long userId, DeleteRequestDto dto) {
    // 데이터 검증 및 조회
    Plan findPlan = planRepository.findByIdOrElseThrow(planId);

    // 로그인된 User ID 와 접근하는 Plan 의 User ID 일치 여부 검증
    planRepository.validatePlanAccess(planId, userId);

    // dto 에서 받아온 pwd 와 DB 의 pwd 일치하는지 확인
    if (!passwordEncoder.matches(dto.getPwd(), findPlan.getUser().getPwd())) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }

    // 일정 삭제
    planRepository.delete(findPlan);
  }

  // 페이징 조회
  @Override
  public PageResponseDto page(int pageNumber, int pageSize) {
    // pageable 객체 :: 정렬방향과 속성 properties 를 지정하기 위해 Sort 객체를 생성
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

    // pageable 를 통해 Page<Plan> 조회
    Page<Plan> planPage = planRepository.findAll(pageable);

    // User 의 이름과 Comment 의 개수 추가
    List<PlanWithUserAndCommentDto> pageResponseDto = planPage.getContent().stream().map(plan -> {
      Long commentCount = commentRepository.countByPlan_Id(plan.getId());
      String name = plan.getUser().getName();
      return PlanWithUserAndCommentDto.from(name, plan, commentCount);
    }).toList();

    // DTO 로 변환 후 반환
    return PageResponseDto.from(pageResponseDto, pageNumber, pageSize, planPage.getTotalPages(), planPage.getTotalElements());
  }
}
