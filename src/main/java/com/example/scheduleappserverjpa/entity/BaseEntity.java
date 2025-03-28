package com.example.scheduleappserverjpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 작성일과 수정일을 자동으로 만들어 주기 위한 BaseEntity
@Getter
@MappedSuperclass // 공통 Entity 속성을 정의할 때 사용하는 어노테이션.
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;
}
