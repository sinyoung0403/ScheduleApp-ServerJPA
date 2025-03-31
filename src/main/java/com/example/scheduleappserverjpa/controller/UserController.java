package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.common.Const;
import com.example.scheduleappserverjpa.dto.user.*;
import com.example.scheduleappserverjpa.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto dto) {
    return new ResponseEntity<>(userService.signUp(dto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<FindResponseDto>> findAll() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FindResponseDto> findById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
  }


  @PatchMapping("/{id}")
  public ResponseEntity<Void> update(
          @PathVariable Long id,
          @RequestBody UpdateRequestDto dto
  ) {
    userService.update(id, dto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<Void> login(
          @RequestBody LoginRequestDto dto,
          HttpServletRequest request,
          HttpServletResponse response) {
    FindResponseDto findResponseDto = userService.login(dto); // 실패시 ? > 에러가 뜰거임.

    // 세션을 만들어서.
    HttpSession session = request.getSession();

    // 로그인한 회원 정보를 저장하고.
    session.setAttribute(Const.LOGIN_USER, findResponseDto);

//    Cookie cookie = new Cookie("userId", String.valueOf(findResponseDto.getId()));

    // Response Set-Cookie: userId=1 형태로 전달된다.

//    response.addCookie(cookie);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  // 로그아웃
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if(session != null) {
      session.invalidate(); // 해당 세션(데이터)을 삭제한다.
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
