package com.example.scheduleappserverjpa.service;


import com.example.scheduleappserverjpa.dto.plan.request.DeleteRequestDto;
import com.example.scheduleappserverjpa.dto.plan.request.SaveRequestDto;
import com.example.scheduleappserverjpa.dto.plan.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.plan.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.plan.response.PageResponseDto;
import com.example.scheduleappserverjpa.dto.plan.response.SaveResponseDto;

import java.util.List;

// "일정의 종류가 다양해질 가능성이 있느냐?" 
// => 없기는 함. 하지만, 인터페이스와 그 구현체를 연습하기 위해서 해당 부분을 인터페이스로 선언할 것임.
public interface PlanService {

  SaveResponseDto savePlan(SaveRequestDto dto, Long loginId);

  List<FindResponseDto> findByAll();

  FindResponseDto findById(Long id);

  void updatePlan(Long planId, Long userId, UpdateRequestDto dto);

  void deletePlan(Long planId, Long userId, DeleteRequestDto dto);

  PageResponseDto page(int pageNumber, int pageSize);
}
