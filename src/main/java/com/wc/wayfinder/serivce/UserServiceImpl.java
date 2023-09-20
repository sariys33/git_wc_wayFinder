package com.wc.wayfinder.serivce;

import com.wc.wayfinder.domain.Managers;
import com.wc.wayfinder.domain.Users;
import com.wc.wayfinder.dto.Role;
import com.wc.wayfinder.dto.UserForm;
import com.wc.wayfinder.dto.UserResponse;
import com.wc.wayfinder.repository.ManagerRepository;
import com.wc.wayfinder.repository.ReviewRepository;
import com.wc.wayfinder.repository.UserRepository;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerRepository managerRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void save(UserForm form) {
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        form.setRole(Role.USER);
        userRepository.save(form.toEntity());
    }

    @Override
    public boolean checkPassword(Long id, String password) {
        return passwordEncoder.matches(password, userRepository.getPasswordById(id));
    }

    @Override
    public UserResponse findById(Long id) {
        return new UserResponse(userRepository.findById(id));
    }

    @Override
    public void remove(UserResponse user) {
        int result = userRepository.remove(user.getId());
        if(result == 1) {
            reviewRepository.removeAllAsWriter(user.getId());
        }
    }

    @Override
    public int modifyPassword(UserForm user) {
        return userRepository.modifyPassword(user.getId(), passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public UserResponse findByEmail(String email) {
        @Nullable
        Users findUser = userRepository.findByEmail(email);
        if(findUser == null) {
            @Nullable
            Managers findMgr = managerRepository.findByEmail(email);
            if(findMgr == null) {
                return null;
            }
        }
        return new UserResponse(findUser);
    }


}
