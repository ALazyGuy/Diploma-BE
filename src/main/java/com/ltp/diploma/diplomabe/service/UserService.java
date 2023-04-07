package com.ltp.diploma.diplomabe.service;

import com.ltp.diploma.diplomabe.model.dto.JWTResponseDto;
import com.ltp.diploma.diplomabe.model.dto.UserRegistrationRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    JWTResponseDto register(UserRegistrationRequestDto userRegistrationRequest);
}
