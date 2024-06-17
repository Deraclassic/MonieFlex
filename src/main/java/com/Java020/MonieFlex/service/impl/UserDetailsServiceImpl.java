package com.Java020.MonieFlex.service.impl;

import com.Java020.MonieFlex.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return customerRepository.findByEmail(userEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }
}
