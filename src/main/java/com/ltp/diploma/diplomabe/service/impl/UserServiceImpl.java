package com.ltp.diploma.diplomabe.service.impl;

import com.ltp.diploma.diplomabe.entity.UserEntity;
import com.ltp.diploma.diplomabe.exception.ConflictException;
import com.ltp.diploma.diplomabe.exception.InvalidCredentialsException;
import com.ltp.diploma.diplomabe.model.UserDetailsImpl;
import com.ltp.diploma.diplomabe.model.dto.JWTResponseDto;
import com.ltp.diploma.diplomabe.model.dto.UserLoginRequestDto;
import com.ltp.diploma.diplomabe.model.dto.UserRegistrationRequestDto;
import com.ltp.diploma.diplomabe.repository.UserRepository;
import com.ltp.diploma.diplomabe.service.UserService;
import com.ltp.diploma.diplomabe.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JWTResponseDto register(final UserRegistrationRequestDto userRegistrationRequest) {
        final UserEntity userEntity = new UserEntity();
        if(userRepository.existsByUsername(userRegistrationRequest.getUsername())){
            throw new ConflictException();
        }
        userEntity.setUsername(userRegistrationRequest.getUsername());
        userEntity.setPassword_hash(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        userRepository.save(userEntity);

        return new JWTResponseDto(JWTUtils.generate(new UserDetailsImpl(userEntity)));
    }

    @Override
    public JWTResponseDto login(final UserLoginRequestDto userLoginRequestDto) {
        if(!userRepository.existsByUsername(userLoginRequestDto.getUsername())){
            throw new InvalidCredentialsException();
        }

        final UserEntity userEntity = userRepository.findByUsername(userLoginRequestDto.getUsername()).get();

        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), userEntity.getPassword_hash())){
            throw new InvalidCredentialsException();
        }

        return new JWTResponseDto(JWTUtils.generate(new UserDetailsImpl(userEntity)));
    }
}
