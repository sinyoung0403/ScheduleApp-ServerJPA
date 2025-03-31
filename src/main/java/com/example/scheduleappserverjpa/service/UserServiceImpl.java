package com.example.scheduleappserverjpa.service;

import com.example.scheduleappserverjpa.config.PasswordEncoder;
import com.example.scheduleappserverjpa.dto.user.*;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.InvalidPasswordException;
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
  private final PasswordEncoder passwordEncoder;

  @Override
  public SignUpResponseDto signUp(SignUpRequestDto dto) {
    // pwd 암호화
    String encode = passwordEncoder.encode(dto.getPwd());
    // user Entity 생성
    User user = new User(dto.getName(), dto.getEmail(), encode);
    // Save Repository
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
    // dto valid 확인
    if (!dto.isValid()) {
      throw new InvalidRequestException("입력값이 잘못되었습니다.");
    }

    // id 에 해당하는 유저 조회
    User findUser = userRepository.findByIdOrElseThrow(id);

    // 해당 유저의 pwd 를 비교하기
    Boolean isMatch = passwordEncoder.matches(dto.getPwd(), findUser.getPwd());

    if(!isMatch) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }

    // 만약 널 값 ?비어있다면, 그냥 기존의 값을 유지하도록
    String setName = (StringUtils.isEmpty(dto.getName())) ? findUser.getName() : dto.getName();
    String setEmail = (StringUtils.isEmpty(dto.getEmail())) ? findUser.getEmail() : dto.getEmail();

    // 그리고 해당 값을 추가해줍니다.
    findUser.updateName(setName);
    findUser.updateEmail(setEmail);
  }

  @Override
  public void delete(Long id, DeleteRequestDto dto) {
    // id 에 해당하는 유저 조회
    User findUser = userRepository.findByIdOrElseThrow(id);

    // 해당 유저의 pwd 를 비교하기
    Boolean isMatch = passwordEncoder.matches(dto.getPwd(), findUser.getPwd());

    if(!isMatch) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }

    userRepository.delete(findUser);
  }

  @Transactional
  @Override
  public FindResponseDto login(LoginRequestDto dto) {
    // 이메일에 맞는 유저 찾기
    User findUser = userRepository.findByEmailOrElseThrow(dto.getEmail());

    // 유저의 비밀번호를 가져와서 비교
    boolean isMatch = passwordEncoder.matches(dto.getPwd(), findUser.getPwd());

    if(!isMatch) {
      throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
    }
    return FindResponseDto.from(findUser);
  }
}
