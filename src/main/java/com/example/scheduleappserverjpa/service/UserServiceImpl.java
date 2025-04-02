package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.config.PasswordEncoder;
import com.example.scheduleappserverjpa.dto.user.*;
import com.example.scheduleappserverjpa.dto.user.request.DeleteRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.LoginRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.SignUpRequestDto;
import com.example.scheduleappserverjpa.dto.user.request.UpdateRequestDto;
import com.example.scheduleappserverjpa.dto.user.response.FindResponseDto;
import com.example.scheduleappserverjpa.dto.user.response.SignUpResponseDto;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.InvalidPasswordException;
import com.example.scheduleappserverjpa.exception.InvalidRequestException;
import com.example.scheduleappserverjpa.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /* 회원가입 */
  @Override
  public SignUpResponseDto signUp(SignUpRequestDto dto) {
    // 데이터 검증
    userRepository.checkEmailDuplicate(dto.getEmail());
    // pwd 암호화
    String encode = passwordEncoder.encode(dto.getPwd());
    // user Entity 생성
    User user = new User(dto.getName(), dto.getEmail(), encode);
    // Save Repository
    User saved = userRepository.save(user);
    return SignUpResponseDto.from(saved);
  }

  /* 모든 유저 조회 */
  @Override
  public List<FindResponseDto> findAll() {
    return userRepository.findAll().stream()
            .map(FindResponseDto::from)
            .toList();
  }

  /* 단건 유저 조회 */
  @Override
  public FindResponseDto findById(Long id) {
    return FindResponseDto.from(userRepository.findByIdOrElseThrow(id));
  }

  /* 유저 수정 */
  @Transactional
  @Override
  public void update(Long id, UpdateRequestDto dto) {
    // DTO 데이터 검증
    if (!dto.isValid()) {
      throw new InvalidRequestException("입력값이 잘못되었습니다.");
    }

    // 데이터 검증 및 조회
    User findUser = userRepository.findByIdOrElseThrow(id);

    // 입력한 PWD 검증
    if (!passwordEncoder.matches(dto.getPwd(), findUser.getPwd())) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }

    // 기존 값을 유지하거나 입력된 값으로 변경
    String setName = (StringUtils.isEmpty(dto.getName())) ? findUser.getName() : dto.getName();
    String setEmail = (StringUtils.isEmpty(dto.getEmail())) ? findUser.getEmail() : dto.getEmail();

    // 유저 업데이트
    findUser.updateName(setName);
    findUser.updateEmail(setEmail);
  }

  /* 유저 삭제 */
  @Override
  public void delete(Long id, DeleteRequestDto dto) {
    // 데이터 검증 및 조회
    User findUser = userRepository.findByIdOrElseThrow(id);

    // 입력한 PWD 검증
    if (!passwordEncoder.matches(dto.getPwd(), findUser.getPwd())) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }

    // 유저 삭제
    userRepository.delete(findUser);
  }

  /* 로그인 */
  @Override
  public LoginDto login(LoginRequestDto dto) {
    // 데이터 검증 및 조회
    User findUser = userRepository.findByEmailOrElseThrow(dto.getEmail());

    // 입력한 PWD 검증
    if (!passwordEncoder.matches(dto.getPwd(), findUser.getPwd())) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }

    // DTO 로 변환 후 반환
    return LoginDto.from(findUser);
  }
}
