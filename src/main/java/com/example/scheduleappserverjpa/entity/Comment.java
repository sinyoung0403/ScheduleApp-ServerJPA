package com.example.scheduleappserverjpa.entity;

import com.example.scheduleappserverjpa.dto.comment.request.CreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "plan_id")
  private Plan plan;

  public Comment(CreateRequestDto dto) {
    this.content = dto.getContent();
  }

  // 연관관계 설정
  public void setUser(User user) {
    this.user = user;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  public void updateContent(String content) {
    this.content = content;
  }
}
