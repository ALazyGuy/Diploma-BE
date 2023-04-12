package com.ltp.diploma.diplomabe.service;

import com.ltp.diploma.diplomabe.entity.TestEntity;
import com.ltp.diploma.diplomabe.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserService {
    JWTResponseDto register(UserRegistrationRequestDto userRegistrationRequest);
    JWTResponseDto login(UserLoginRequestDto userLoginRequestDto);
    UserInfoResponseDto info();
    void saveUserTestResult(UserTestResultRequestDto userTestResultRequestDto);
    Set<TestEntity> loadTests();
    JWTResponseDto updateUserInfo(UserUpdateRequestDto userUpdateRequestDto);
}
