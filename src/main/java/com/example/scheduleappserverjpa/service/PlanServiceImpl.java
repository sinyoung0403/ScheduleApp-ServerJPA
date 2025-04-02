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
    // 로그인 세션에 맞는 유저 Id Data 조회
    User findUser = userRepository.findByIdOrElseThrow(loginId);
    // Dto 에 맞는 Entity 생성
    Plan plan = new Plan(dto.getTitle(), dto.getContents());
    plan.setUser(findUser);
    // DB 저장
    Plan saved = planRepository.save(plan);
    return SaveResponseDto.from(saved);
  }

  // 일정 전체 조회
  @Override
  public List<FindResponseDto> findByAll() {
    return planRepository.findAll().stream().map(FindResponseDto::from).toList();
  }

  // 일정 단건 조회
  @Override
  public FindResponseDto findById(Long id) {
    Plan findPlan = planRepository.findByIdOrElseThrow(id);
    return from(findPlan);
  }

  // 일정 수정
  @Transactional
  @Override
  public void updatePlan(Long planId, Long userId, UpdateRequestDto dto) {
    // 값이 유효하지 않다면, 에러 반환
    if (!dto.isValid()) {
      throw new InvalidRequestException("입력 값이 유효하지 않습니다. 둘 중 하나의 값은 입력해야 합니다.");
    }

    // 일정 찾기
    Plan findPlan = planRepository.findByIdOrElseThrow(planId);

    // 해당 일정의 유저 아이디와 로그인된 유저 아이디 일치 여부 확인
    validatePlanAccess(planId, userId);

    // 널 값이라면 그냥 기존의 값을 유지 || 새로운 값이 들어왔으면 새로운 값 지정
    String setTitle = (StringUtils.isEmpty(dto.getTitle())) ? findPlan.getTitle() : dto.getTitle();
    String setContents = (StringUtils.isEmpty(dto.getContents())) ? findPlan.getContents() : dto.getContents();

    // update
    findPlan.updateTitle(setTitle);
    findPlan.updateContents(setContents);
  }

  // 일정 삭제
  @Override
  public void deletePlan(Long planId, Long userId, DeleteRequestDto dto) {
    // 일정 찾기
    Plan findPlan = planRepository.findByIdOrElseThrow(planId);

    // 해당 일정의 유저 아이디와 로그인된 유저 아이디 일치 여부 확인
    validatePlanAccess(planId, userId);

    // dto 에서 받아온 pwd 와 DB 의 pwd 일치하는지 확인
    if (!passwordEncoder.matches(dto.getPwd(), findPlan.getUser().getPwd())) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }

    planRepository.delete(findPlan);
  }

  // 페이징 조회
  @Override
  public PageResponseDto page(int pageNumber, int pageSize) {
    // pageable 객체 :: 정렬방향과 속성 properties 를 지정하기 위해 Sort 객체를 생성
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

    // pageable 를 통해 Page<Plan> 조회
    Page<Plan> planPage = planRepository.findAll(pageable);

    // User 의 이름과 Comment 의 개수 포함
    List<PlanWithUserAndCommentDto> pageResponseDto = planPage.getContent().stream().map(plan -> {
      Long commentCount = commentRepository.countByPlan_Id(plan.getId());
      String name = plan.getUser().getName();
      return PlanWithUserAndCommentDto.from(name, plan, commentCount);
    }).toList();

    // DTO 로 변환
    return PageResponseDto.from(pageResponseDto, pageNumber, pageSize, planPage.getTotalPages(), planPage.getTotalElements());
  }

  // 일정과 권한 확인
  private void validatePlanAccess(Long planId, Long userId) {
    if (!planRepository.existsByIdAndUser_Id(planId, userId)) {
      throw new UnauthorizedAccessException("권한이 없는 일정에 접근 하셨습니다.");
    }
  }

}
