package com.ltp.diploma.diplomabe.service.impl;

import com.ltp.diploma.diplomabe.entity.TestEntity;
import com.ltp.diploma.diplomabe.entity.UserEntity;
import com.ltp.diploma.diplomabe.exception.ConflictException;
import com.ltp.diploma.diplomabe.exception.InvalidCredentialsException;
import com.ltp.diploma.diplomabe.model.UserDetailsImpl;
import com.ltp.diploma.diplomabe.model.dto.*;
import com.ltp.diploma.diplomabe.repository.TestRepository;
import com.ltp.diploma.diplomabe.repository.UserRepository;
import com.ltp.diploma.diplomabe.service.UserService;
import com.ltp.diploma.diplomabe.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder,
                           final TestRepository testRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.testRepository = testRepository;
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

    @Override
    public UserInfoResponseDto info() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new UserInfoResponseDto(username);
    }

    @Override
    public void saveUserTestResult(final UserTestResultRequestDto userTestResultRequestDto) {
        final int ticket = userTestResultRequestDto.getTicket();
        final int result = userTestResultRequestDto.getResult();
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final UserEntity user = userRepository.findByUsername(username).get();
        final Set<TestEntity> tests = user.getTests();
        final Optional<TestEntity> optTest = tests.stream().filter(test -> test.getTicket() == ticket).findFirst();

        if(optTest.isEmpty()) {
            final TestEntity testEntity = new TestEntity();
            testEntity.setTicket(ticket);
            testEntity.setResult(result);
            testRepository.save(testEntity);
            user.getTests().add(testEntity);
            userRepository.save(user);
        } else {
            final TestEntity testEntity = optTest.get();
            testEntity.setResult(result);
            testRepository.save(testEntity);
            userRepository.save(user);
        }
    }

    @Override
    public Set<TestEntity> loadTests() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).get().getTests();
    }

}
