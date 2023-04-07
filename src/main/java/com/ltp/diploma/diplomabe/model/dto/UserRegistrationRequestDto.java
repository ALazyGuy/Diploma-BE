package com.ltp.diploma.diplomabe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationRequestDto {
    private String username;
    private String password;
}
