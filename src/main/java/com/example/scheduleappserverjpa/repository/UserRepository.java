package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import com.example.scheduleappserverjpa.exception.InvalidPasswordException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  default User findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(() -> new DataNotFoundException(id + ", 해당 유저가 존재하지 않습니다."));
  }

  Optional<User> findUserByName(String name);

  default User findUserByNameOrElseThrow(String name) {
    return findUserByName(name).orElseThrow(() -> new DataNotFoundException(name + ", 해당 이름은 존재하지 않습니다."));
  }

  Optional<User> findByEmailAndPwd(String email, String pwd);

  // login
  default User findByEmailAndPwdOrElseThrow(String email, String pwd) {
    return findByEmailAndPwd(email, pwd).orElseThrow(() -> new InvalidPasswordException(email + ", 해당 이메일과 비밀번호가 일치하지 않습니다."));
  }
}
