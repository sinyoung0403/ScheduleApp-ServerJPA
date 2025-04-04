package com.example.scheduleappserverjpa.config;

import com.example.scheduleappserverjpa.entity.Comment;
import com.example.scheduleappserverjpa.entity.Plan;
import com.example.scheduleappserverjpa.entity.User;
import com.example.scheduleappserverjpa.repository.CommentRepository;
import com.example.scheduleappserverjpa.repository.PlanRepository;
import com.example.scheduleappserverjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
  private final UserRepository userRepository;
  private final PlanRepository planRepository;
  private final CommentRepository commentRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    commentRepository.deleteAll();
    planRepository.deleteAll();
    userRepository.deleteAll();

    for (int i = 1; i <= 5; i++) {
      String name = "test" + i;
      String email = "test" + i + "@test.com";
      String rawPassword = "pwd";
      String encodedPassword = passwordEncoder.encode(rawPassword);

      User user = new User(name, email, encodedPassword);
      userRepository.save(user);

      // 사용자에게 할 일 추가
      for (int j = 1; j <= 2; j++) {
        Plan plan = new Plan("할 일 " + j + " for " + email, "할일");
        plan.setUser(user);
        planRepository.save(plan);

        // 할 일에 댓글 추가
        for (int k = 1; k <= 2; k++) {
          Comment comment = new Comment("댓글 " + k + " on " + plan.getTitle());
          comment.setPlan(plan);
          comment.setUser(user);
          commentRepository.save(comment);
        }
      }
    }
    log.info("초기 데이터 생성완료");
  }
}
