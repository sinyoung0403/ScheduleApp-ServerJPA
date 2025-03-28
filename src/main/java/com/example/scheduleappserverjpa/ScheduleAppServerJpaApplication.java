package com.example.scheduleappserverjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 작성일과 수정일을 업데이트하기 위해서는 해당 어노테이션을 메인에 넣어야함.
@SpringBootApplication
public class ScheduleAppServerJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScheduleAppServerJpaApplication.class, args);
  }

}
