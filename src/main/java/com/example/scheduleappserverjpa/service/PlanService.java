package com.example.scheduleappserverjpa.service;


import com.example.scheduleappserverjpa.dto.plan.FindResponseDto;
import com.example.scheduleappserverjpa.dto.plan.SaveResponseDto;
import com.example.scheduleappserverjpa.dto.plan.UpdateRequestDto;

import java.util.List;

// "일정의 종류가 다양해질 가능성이 있느냐?" 
// => 없기는 함. 하지만, 인터페이스와 그 구현체를 연습하기 위해서 해당 부분을 인터페이스로 선언할 것임.
public interface PlanService {

  SaveResponseDto savePlan(String username, String title, String contents);

  List<FindResponseDto> findByAll();

  FindResponseDto findById(Long id);

  void updatePlan(Long id, UpdateRequestDto dto);

  void deletePlan(Long id);
}
