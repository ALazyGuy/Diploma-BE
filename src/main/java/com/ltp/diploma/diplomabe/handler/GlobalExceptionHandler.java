package com.ltp.diploma.diplomabe.handler;

import com.ltp.diploma.diplomabe.exception.ConflictException;
import com.ltp.diploma.diplomabe.exception.InvalidCredentialsException;
import com.ltp.diploma.diplomabe.model.dto.ErrorResponseDto;
import com.ltp.diploma.diplomabe.model.dto.ErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> conflict(){
        return ResponseEntity.status(200).body(new ErrorResponseDto(ErrorType.USERNAME_CONFLICT));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> invalidCredentials(){
        return ResponseEntity.status(200).body(new ErrorResponseDto(ErrorType.INVALID_CREDENTIALS));
    }

}
