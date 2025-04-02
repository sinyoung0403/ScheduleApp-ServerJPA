package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.common.Const;

import com.example.scheduleappserverjpa.dto.user.*;
import com.example.scheduleappserverjpa.dto.user.request.DeleteRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.LoginRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.SignUpRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.user.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.user.response.SignUpResponseDto;
import com.example.scheduleappserverjpa.exception.InvalidRequestException;
import com.example.scheduleappserverjpa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /* 회원가입 */
  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto dto) {
    return new ResponseEntity<>(userService.signUp(dto), HttpStatus.CREATED);
  }

  /* 모든 유저 조회 */
  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findAll() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }

  /* 단건 유저 조회 */
  @GetMapping("/{id}")
  public ResponseEntity<FindResponseDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  /* 유저 수정 */
  @PatchMapping
  public ResponseEntity<String> update(
          @Valid @RequestBody UpdateRequestDto dto,
          HttpServletRequest request
  ) {
    LoginDto loginUser = (LoginDto) request.getSession().getAttribute("loginUser");
    userService.update(loginUser.getId(), dto);
    return ResponseEntity.ok(loginUser.getId() + " 유저를 수정했습니다.");
  }

  /* 유저 삭제 */
  @DeleteMapping
  public ResponseEntity<String> delete(
          @Valid @RequestBody DeleteRequestDto dto,
          HttpServletRequest request) {
    LoginDto loginDto = (LoginDto) request.getSession().getAttribute("loginUser");
    userService.delete(loginDto.getId(), dto);
    return ResponseEntity.ok(loginDto.getId() + " 유저를 삭제했습니다.");
  }

  /* 로그인 */
  @PostMapping("/login")
  public ResponseEntity<String> login(
          @Valid @RequestBody LoginRequestDto dto,
          HttpServletRequest request) {
    HttpSession session = request.getSession();

    // 로그인 중이라면 Bad_Request
    if (session.getAttribute("loginUser") != null) {
      throw new InvalidRequestException("이미 로그인된 상태입니다.");
    }

    LoginDto loginDto = userService.login(dto);
    session.setAttribute(Const.LOGIN_USER, loginDto);
    return ResponseEntity.ok("로그인했습니다.");
  }

  /* 로그아웃 */
  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return ResponseEntity.ok("로그아웃 했습니다.");
  }
}
