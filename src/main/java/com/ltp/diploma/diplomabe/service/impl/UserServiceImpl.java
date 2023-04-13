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

import java.time.LocalDateTime;
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
        if (userRepository.existsByUsername(userRegistrationRequest.getUsername())) {
            throw new ConflictException();
        }
        userEntity.setUsername(userRegistrationRequest.getUsername());
        userEntity.setPassword_hash(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        userRepository.save(userEntity);

        return new JWTResponseDto(JWTUtils.generate(new UserDetailsImpl(userEntity)));
    }

    @Override
    public JWTResponseDto login(final UserLoginRequestDto userLoginRequestDto) {
        if (!userRepository.existsByUsername(userLoginRequestDto.getUsername())) {
            throw new InvalidCredentialsException();
        }

        final UserEntity userEntity = userRepository.findByUsername(userLoginRequestDto.getUsername()).get();

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), userEntity.getPassword_hash())) {
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

        if(tests.size() == 10) {
            final TestEntity first = tests.stream()
                    .sorted((t1, t2) -> t1.getId() < t2.getId() ? -1 : 1)
                    .findFirst().get();
            user.getTests().remove(first);
            testRepository.delete(first);
        }

        final TestEntity testEntity = new TestEntity();
        testEntity.setTicket(ticket);
        testEntity.setResult(result);
        testEntity.setDate(LocalDateTime.now());
        testRepository.save(testEntity);
        user.getTests().add(testEntity);

        userRepository.save(user);
    }

    @Override
    public Set<TestEntity> loadTests() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).get().getTests();
    }

    @Override
    public JWTResponseDto updateUserInfo(final UserUpdateRequestDto userUpdateRequestDto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final UserEntity user = userRepository.findByUsername(username).get();

        if(!userUpdateRequestDto.getUsername().isEmpty() && !userRepository.existsByUsername(userUpdateRequestDto.getUsername())) {
            user.setUsername(userUpdateRequestDto.getUsername());
        }

        if(!userUpdateRequestDto.getPassword().isEmpty()) {
            user.setPassword_hash(passwordEncoder.encode(userUpdateRequestDto.getPassword()));
        }

        userRepository.save(user);
        return new JWTResponseDto(JWTUtils.generate(new UserDetailsImpl(user)));
    }

    @Override
    public void deleteUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final UserEntity user = userRepository.findByUsername(username).get();
        userRepository.delete(user);
    }
}
