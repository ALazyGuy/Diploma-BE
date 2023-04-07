package com.ltp.diploma.diplomabe.service;

import com.ltp.diploma.diplomabe.model.dto.UserRegistrationRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Long register(UserRegistrationRequestDto userRegistrationRequest);
}
