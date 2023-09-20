package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Managers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepository{

    private final ManagerMapper managerMapper;

    @Override
    public void save(Managers manager) {
        managerMapper.save(manager);
    }

    @Override
    public Managers findByEmail(String email) {
        return managerMapper.findByEmail(email);
    }

    @Override
    public String getPasswordById(Long id) {
        return managerMapper.getPasswordById(id);
    }

    @Override
    public Managers findById(Long id) {
        return managerMapper.findById(id);
    }

    @Override
    public void remove(Long id) {
        managerMapper.remove(id);
    }

    @Override
    public int modifyPassword(Long id, String password) {
        return managerMapper.modifyPassword(id, password);
    }

}
