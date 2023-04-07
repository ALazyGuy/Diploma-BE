package com.ltp.diploma.diplomabe.controller;

import com.ltp.diploma.diplomabe.model.dto.UserRegistrationRequestDto;
import com.ltp.diploma.diplomabe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = {APPLICATION_JSON_VALUE})
    public Long register(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto){
        return userService.register(userRegistrationRequestDto);
    }
}
