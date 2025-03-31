package com.example.scheduleappserverjpa.repository;

import com.example.scheduleappserverjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  default User findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  Optional<User> findUserByName(String name);

  default User findUserByNameOrElseThrow(String name) {
    return findUserByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  Optional<User> findByEmailAndPwd(String email, String pwd);

  // login
  default User findByEmailAndPwdOrElseThrow(String email, String pwd) {
    return findByEmailAndPwd(email, pwd).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
  }
}
