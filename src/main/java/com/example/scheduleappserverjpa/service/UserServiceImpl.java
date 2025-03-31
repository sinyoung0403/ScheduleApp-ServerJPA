package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.dto.user.*;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.InvalidRequestException;
import com.example.scheduleappserverjpa.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public SignUpResponseDto signUp(SignUpRequestDto dto) {
    User user = new User(dto.getName(), dto.getEmail(), dto.getPwd());
    User saved = userRepository.save(user);
    return SignUpResponseDto.from(saved);
  }

  @Override
  public List<FindResponseDto> findAll() {
    List<User> list = userRepository.findAll();
    return list.stream().map(FindResponseDto::from).toList();
  }

  @Override
  public FindResponseDto findById(Long id) {
    User user = userRepository.findByIdOrElseThrow(id);
    return FindResponseDto.from(user);
  }

  @Transactional
  @Override
  public void update(Long id, UpdateRequestDto dto) {
    // id 를 먼저 찾기
    User findUser = userRepository.findByIdOrElseThrow(id);

    // 그 후 update 해주기
    if (!dto.isValid()) {
      throw new InvalidRequestException("입력값이 잘못되었습니다.");
    }

    // 만약 널 값 ?비어있다면, 그냥 기존의 값을 유지하도록
    String setName = (StringUtils.isEmpty(dto.getName())) ? findUser.getName() : dto.getName();
    String setEmail = (StringUtils.isEmpty(dto.getEmail())) ? findUser.getEmail() : dto.getEmail();

    // 그리고 해당 값을 추가해줍니다.
    findUser.updateName(setName);
    findUser.updateEmail(setEmail);
  }

  @Override
  public void delete(Long id) {
    User findUser = userRepository.findByIdOrElseThrow(id);
    userRepository.delete(findUser);
  }

  @Override
  public FindResponseDto login(LoginRequestDto dto) {
    // 아이디랑, 찾은 후에 ? return 해주면 됩니다.
    User findUser = userRepository.findByEmailAndPwdOrElseThrow(dto.getEmail(), dto.getPwd());
    return FindResponseDto.from(findUser);
  }
}
