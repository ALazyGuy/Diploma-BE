package com.ltp.diploma.diplomabe.handler;

import com.ltp.diploma.diplomabe.exception.ConflictException;
import com.ltp.diploma.diplomabe.model.dto.ErrorResponseDto;
import com.ltp.diploma.diplomabe.model.dto.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorResponseDto> conflict(){
        return ResponseEntity.status(200).body(new ErrorResponseDto(ErrorType.USERNAME_CONFLICT));
    }

}
