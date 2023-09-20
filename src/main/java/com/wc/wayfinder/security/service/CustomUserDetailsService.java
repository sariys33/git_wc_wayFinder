package com.wc.wayfinder.security.service;

import com.wc.wayfinder.domain.Managers;
import com.wc.wayfinder.domain.Users;
import com.wc.wayfinder.repository.ManagerRepository;
import com.wc.wayfinder.repository.UserRepository;
import com.wc.wayfinder.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;

    // 시큐리티가 로그인 처리 후, 자동으로 호출되는 메서드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users findUser = userRepository.findByEmail(email);
        Managers findManager = managerRepository.findByEmail(email);
        if (findUser != null) {
            return new CustomUser(findUser);
        } else if (findManager != null) {
            return new CustomUser(findManager);
        } else {
            throw new UsernameNotFoundException("해당 계정 없음 -> " + email);
        }
    }

}
