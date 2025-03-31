package com.example.scheduleappserverjpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)

  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void updateEmail(String email) {
    this.email = email;
  }
}
