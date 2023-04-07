package com.ltp.diploma.diplomabe.service.impl;

import com.ltp.diploma.diplomabe.entity.UserEntity;
import com.ltp.diploma.diplomabe.model.dto.UserRegistrationRequestDto;
import com.ltp.diploma.diplomabe.repository.UserRepository;
import com.ltp.diploma.diplomabe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long register(final UserRegistrationRequestDto userRegistrationRequest) {
        final UserEntity userEntity = new UserEntity();
        if(userRepository.existsByUsername(userRegistrationRequest.getUsername())){
            return -1L;
        }
        userEntity.setUsername(userRegistrationRequest.getUsername());
        userEntity.setPassword_hash(userRegistrationRequest.getPassword());
        return userRepository.save(userEntity).getId();
    }
}
