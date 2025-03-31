package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.plan.FindResponseDto;
import com.example.scheduleappserverjpa.dto.plan.SaveResponseDto;
import com.example.scheduleappserverjpa.dto.plan.UpdateRequestDto;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.repository.PlanRepository;
import com.example.scheduleappserverjpa.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.scheduleappserverjpa.dto.plan.FindResponseDto.from;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;
  private final UserRepository userRepository;

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
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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
}
