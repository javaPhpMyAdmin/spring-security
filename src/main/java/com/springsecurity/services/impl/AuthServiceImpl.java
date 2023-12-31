package com.springsecurity.services.impl;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.DTO.LoginDTO;
import com.springsecurity.DTO.LoginResponseDto;
import com.springsecurity.DTO.RegisterDTO;
import com.springsecurity.DTO.RegisterResponseDto;
import com.springsecurity.entities.UserEntity;
import com.springsecurity.repositories.AuthRepository;
import com.springsecurity.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthRepository authRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  public LoginResponseDto login(LoginDTO loginDto) {

    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
              loginDto.getPassword()));

      UserEntity user = authRepository.findByEmail(loginDto.getEmail()).orElseThrow();

      String token = jwtService.generateToken(user);

      return LoginResponseDto.builder()
          .token(token)
          .build();

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("ERROR TRYING TO LOGIN THE USER: " + e);
      throw e;
    }

  }

  @Override
  public RegisterResponseDto register(RegisterDTO registerDto) {

    try {
      UserEntity user = UserEntity.builder()
          .email(registerDto.getEmail())
          .firstName(registerDto.getFirstName())
          .lastName(registerDto.getLastName())
          .password(passwordEncoder.encode(registerDto.getPassword()))
          .role(registerDto.getRole())
          .build();

      authRepository.save(user);
      return RegisterResponseDto.builder()
          .token(jwtService.generateToken(user))
          .build();

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("ERROR TRYING TO REGISTER USER :" + e);
      throw e;
    }

  }
}
