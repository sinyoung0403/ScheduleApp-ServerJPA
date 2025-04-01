package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.plan.*;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.InvalidRequestException;
import com.example.scheduleappserverjpa.repository.CommentRepository;
import com.example.scheduleappserverjpa.repository.PlanRepository;
import com.example.scheduleappserverjpa.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.scheduleappserverjpa.dto.plan.FindResponseDto.from;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  @Override
  public SaveResponseDto savePlan(String username, String title, String contents) {
    // userName 으로 Entity 를 찾기
    User findUser = userRepository.findUserByNameOrElseThrow(username);
    Plan plan = new Plan(title, contents);
    plan.setUser(findUser);
    Plan saved = planRepository.save(plan);
    return SaveResponseDto.from(saved);
  }

  @Override
  public List<FindResponseDto> findByAll() {
    return planRepository.findAll().stream().map(FindResponseDto::from).toList();
  }

  @Override
  public FindResponseDto findById(Long id) {
    Plan findPlan = planRepository.findByIdOrElseThrow(id);
    return from(findPlan);
  }

  @Transactional
  @Override
  public void updatePlan(Long id, UpdateRequestDto dto) {
    // 후에 꼭 물어봐야할것.
    // 컨트롤러에서 담은 dto 를 그대로 서비스에 넘기는게 나을지?
    // 아니면 컨트롤러에서 담은 dto 를 풀어서 (string, Long, 이런식으로) 넘기는게 나을지
    // 음.. update 할려면 해당 id 를 가진 Entity 를 가져와서, 그 entity 의 title 과 contents 를 수정해주면 됨.
    Plan findPlan = planRepository.findByIdOrElseThrow(id);

    // 값이 유효하지 않다면, 에러 반환
    if (!dto.isValid()) {
      throw new InvalidRequestException("입력 값이 유효하지 않습니다. 둘 중 하나의 값은 입력해야 합니다.");
    }

    // 만약 널 값 ?비어있다면, 그냥 기존의 값을 유지하도록
    String setTitle = (StringUtils.isEmpty(dto.getTitle())) ? findPlan.getTitle() : dto.getTitle();
    String setContents = (StringUtils.isEmpty(dto.getContents())) ? findPlan.getContents() : dto.getContents();

    // 그리고 해당 값을 추가해줍니다.
    findPlan.updateTitle(setTitle);
    findPlan.updateContents(setContents);
  }

  @Override
  public void deletePlan(Long id) {
    Plan findPlan = planRepository.findByIdOrElseThrow(id);
    planRepository.delete(findPlan);
  }

  @Override
  public PageResponseDto page(int pageNumber, int pageSize) {
    // pageable 객체 만들기
    // 정렬방향과 속성 properties 를 지정하려면 Sort 객체를 생성하여야함.
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

    Page<Plan> planPage = planRepository.findAll(pageable);

    List<PlanWithUserAndCommentDto> pageResponseDto = planPage.getContent().stream().map(plan -> {
      Long commentCount = commentRepository.countByPlan_Id(plan.getId());
      String name = plan.getUser().getName();
      return PlanWithUserAndCommentDto.from(name, plan, commentCount);
    }).toList();

    return PageResponseDto.from(pageResponseDto, pageNumber, pageSize, planPage.getTotalPages(), planPage.getTotalElements());
  }
  // 리스트로 페이지 ? 관련된 부분도 넘겨주고싶은데.

}
