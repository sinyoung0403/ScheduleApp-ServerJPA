package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.exception.DataNotFoundException;
import com.example.scheduleappserverjpa.exception.InvalidPasswordException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  // id 로 찾기
  default User findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(() -> new DataNotFoundException(id + ", 해당 유저가 존재하지 않습니다."));
  }

  // Name 으로 찾기
  Optional<User> findUserByName(String name);

  default User findUserByNameOrElseThrow(String name) {
    return findUserByName(name).orElseThrow(() -> new DataNotFoundException(name + ", 해당 이름은 존재하지 않습니다."));
  }

  // login : email 과 pwd 일치하는지 확인
  Optional<User> findByEmailAndPwd(String email, String pwd);

  default User findByEmailAndPwdOrElseThrow(String email, String pwd) {
    return findByEmailAndPwd(email, pwd).orElseThrow(() -> new InvalidPasswordException(email + ", 해당 이메일과 비밀번호가 일치하지 않습니다."));
  }

  // Id 와 Pwd 비교하여 일치하는지 확인
  Optional<User> findByIdAndPwd(Long id, String pwd);

  default User findByIdAndPwdOrElseThrow(Long id, String pwd) {
    return findByIdAndPwd(id, pwd).orElseThrow(()-> new InvalidPasswordException(id + ", 해당 유저와 비밀번호가 일치하지 않습니다."));
  }

  Optional<User> findByEmail(String email);

  default User findByEmailOrElseThrow(String email) {
    return findByEmail(email).orElseThrow(()->new DataNotFoundException("해당하는 이메일이 존재하지 않습니다."));
  }
  User findDistinctByEmail(String email);
}
