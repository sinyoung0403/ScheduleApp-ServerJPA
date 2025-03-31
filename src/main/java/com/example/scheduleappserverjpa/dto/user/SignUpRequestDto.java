package com.example.scheduleappserverjpa.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
  @NotBlank(message = "이름은 필수값 입니다.")
  @Size(max = 4, message = "유저명은 4글자 이내여야 합니다.")
  private String name;

  @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}", message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수값 입니다.")
  private String pwd;
}
