package com.springsecurity.services;

import com.springsecurity.DTO.LoginDTO;
import com.springsecurity.DTO.LoginResponseDto;
import com.springsecurity.DTO.RegisterDTO;
import com.springsecurity.DTO.RegisterResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginDTO loginDto);

    RegisterResponseDto register(RegisterDTO registerDto);
}
