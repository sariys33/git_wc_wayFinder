package com.wc.wayfinder.serivce;

import com.wc.wayfinder.domain.Managers;
import com.wc.wayfinder.domain.Users;
import com.wc.wayfinder.dto.ManagerForm;
import com.wc.wayfinder.dto.ManagerResponse;
import com.wc.wayfinder.dto.Role;
import com.wc.wayfinder.repository.ManagerRepository;
import com.wc.wayfinder.repository.UserRepository;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void save(ManagerForm form) {
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        form.setRole(Role.USER);
        managerRepository.save(form.toEntity());
    }

    @Override
    public boolean checkPassword(Long id, String password) {
        return passwordEncoder.matches(password, managerRepository.getPasswordById(id));
    }

    @Override
    public ManagerResponse findById(Long id) {
        return new ManagerResponse(managerRepository.findById(id));
    }

    @Override
    public void remove(ManagerResponse manager) {
        managerRepository.remove(manager.getId());
    }

    @Override
    public int modifyPassword(ManagerForm manager) {
        return managerRepository.modifyPassword(manager.getId(), passwordEncoder.encode(manager.getPassword()));
    }

    @Override
    public ManagerResponse findByEmail(String email) {
        @Nullable
        Managers findMgr = managerRepository.findByEmail(email);
        if(findMgr == null) {
            @Nullable
            Users findUser = userRepository.findByEmail(email);
            if(findUser == null) {
                return null;
            }
        }
        return new ManagerResponse(findMgr);
    }


}
