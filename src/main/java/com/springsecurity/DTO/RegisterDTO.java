package com.springsecurity.DTO;

import com.springsecurity.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
  private String email;
  private String firstName;
  private String lastName;
  private String password;
  private Role role;
}
