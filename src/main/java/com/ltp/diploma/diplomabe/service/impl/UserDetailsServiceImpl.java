package com.ltp.diploma.diplomabe.service.impl;

import com.ltp.diploma.diplomabe.entity.UserEntity;
import com.ltp.diploma.diplomabe.model.UserDetailsImpl;
import com.ltp.diploma.diplomabe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
