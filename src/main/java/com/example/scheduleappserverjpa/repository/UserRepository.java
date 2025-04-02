package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import com.example.scheduleappserverjpa.exception.InvalidPasswordException;
import com.example.scheduleappserverjpa.exception.InvalidRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  /* User id 가 일치하는 유저 */
  default User findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(() -> new DataNotFoundException(id + ", 해당 유저가 존재하지 않습니다."));
  }

  /* 유저 존재 여부 확인 */
  default void validateExistenceById(Long id) {
    if (!existsById(id)) {
      throw new DataNotFoundException("해당 유저가 존재하지 않습니다.");
    }
  }

  /* Email 이 일치하는 유저 */
  Optional<User> findByEmail(String email);

  default User findByEmailOrElseThrow(String email) {
    return findByEmail(email).orElseThrow(() -> new DataNotFoundException("해당하는 이메일이 존재하지 않습니다."));
  }

  /* Email 존재 여부 확인 */
  boolean existsByEmail(String email);

  default void checkEmailDuplicate(String email) {
    if (existsByEmail(email)) {
      throw new InvalidRequestException("이미 존재하는 이메일입니다.");
    }
  }
}
