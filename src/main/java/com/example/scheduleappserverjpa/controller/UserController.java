package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.common.Const;
import com.example.scheduleappserverjpa.config.PasswordEncoder;
import com.example.scheduleappserverjpa.dto.user.*;
import com.example.scheduleappserverjpa.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
  // 비밀번호를 암호화하기 위해서 선언
  private final PasswordEncoder passwordEncoder;

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto dto) {
    return new ResponseEntity<>(userService.signUp(dto), HttpStatus.CREATED);
  }

  // 모든 유저 조회
  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findAll() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }

  // 단건 유저 조회
  @GetMapping("/{id}")
  public ResponseEntity<FindResponseDto> findById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
  }

  // 유저 수정
  @PatchMapping("/{id}")
  public ResponseEntity<String> update(
          @PathVariable Long id,
          @Valid @RequestBody UpdateRequestDto dto
  ) {
    userService.update(id, dto);
    return ResponseEntity.status(HttpStatus.OK).body(id + " 유저를 수정했습니다.");
  }

  // 유저 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
          @PathVariable Long id,
          @Valid @RequestBody DeleteRequestDto dto) {
    userService.delete(id, dto);
    return ResponseEntity.status(HttpStatus.OK).body(id + " 유저를 삭제했습니다.");
  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<String> login(
          @Valid @RequestBody LoginRequestDto dto,
          HttpServletRequest request) {
    FindResponseDto findResponseDto = userService.login(dto);
    HttpSession session = request.getSession();
    session.setAttribute(Const.LOGIN_USER, findResponseDto);
    return ResponseEntity.status(HttpStatus.OK).body("로그인했습니다.");
  }

  // 로그아웃
  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate(); // 해당 세션(데이터)을 삭제한다.
    }
    return ResponseEntity.status(HttpStatus.OK).body("로그아웃 했습니다.");
  }
}
