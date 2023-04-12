package com.ltp.diploma.diplomabe.controller;

import com.ltp.diploma.diplomabe.entity.TestEntity;
import com.ltp.diploma.diplomabe.model.dto.*;
import com.ltp.diploma.diplomabe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.OPTIONS})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public JWTResponseDto register(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto){
        return userService.register(userRegistrationRequestDto);
    }

    @PostMapping(value = "/login", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public JWTResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        return userService.login(userLoginRequestDto);
    }

    @GetMapping(value = "/info", produces = {APPLICATION_JSON_VALUE})
    public UserInfoResponseDto info(){
        return userService.info();
    }

    @PutMapping(value = "/test", consumes = {APPLICATION_JSON_VALUE})
    public void saveTestResult(@RequestBody UserTestResultRequestDto userTestResultRequestDto){
        userService.saveUserTestResult(userTestResultRequestDto);
    }

    @GetMapping(value = "/test", produces = {APPLICATION_JSON_VALUE})
    public Set<TestEntity> getTests(){
        return userService.loadTests();
    }

    @PutMapping(value = "/update", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public JWTResponseDto updateUserInfo(@RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return userService.updateUserInfo(userUpdateRequestDto);
    }

}
