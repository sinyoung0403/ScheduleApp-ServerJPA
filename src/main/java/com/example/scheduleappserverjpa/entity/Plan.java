package com.example.scheduleappserverjpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "plan")
@NoArgsConstructor // 기본생성자
public class Plan extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String contents;

  @ManyToOne
  @JoinColumn(name="userId")
  private User user;

  public Plan(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateContents(String contents) {
    this.contents = contents;
  }

  // member 주입
  public void setUser(User user) {
    this.user = user;
  }
}
