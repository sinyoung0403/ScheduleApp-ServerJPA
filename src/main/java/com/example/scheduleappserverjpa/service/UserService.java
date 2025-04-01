package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.user.*;

import java.util.List;

public interface UserService {
  SignUpResponseDto signUp(SignUpRequestDto dto);

  List<FindResponseDto> findAll();

  FindResponseDto findById(Long id);

  void update(Long id, UpdateRequestDto dto);

  void delete(Long id, DeleteRequestDto dto);

  LoginDto login(LoginRequestDto dto);
}
