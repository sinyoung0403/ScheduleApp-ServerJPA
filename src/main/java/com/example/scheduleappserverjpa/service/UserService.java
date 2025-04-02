package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.user.*;
import com.example.scheduleappserverjpa.dto.user.request.DeleteRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.LoginRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.SignUpRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.user.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.user.response.SignUpResponseDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {
  SignUpResponseDto signUp(SignUpRequestDto dto);

  List<FindResponseDto> findAll();

  FindResponseDto findById(Long id);

  void update(Long id, UpdateRequestDto dto);

  void delete(Long id, DeleteRequestDto dto);

  LoginDto login(LoginRequestDto dto, HttpSession session);

  void logout(HttpSession session);
}
