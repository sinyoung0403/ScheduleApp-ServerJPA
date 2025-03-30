package com.example.scheduleappserverjpa.controller;

import com.example.scheduleappserverjpa.dto.user.FindResponseDto;
import com.example.scheduleappserverjpa.dto.user.SignUpRequestDto;
import com.example.scheduleappserverjpa.dto.user.SignUpResponseDto;
import com.example.scheduleappserverjpa.dto.user.UpdateRequestDto;
import com.example.scheduleappserverjpa.service.UserService;
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
}
