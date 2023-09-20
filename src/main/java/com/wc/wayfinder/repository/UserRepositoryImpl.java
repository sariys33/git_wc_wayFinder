package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final UserMapper userMapper;

    @Override
    public void save(Users user) {
        userMapper.save(user);
    }

    @Override
    public Users findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public String getPasswordById(Long id) {
        return userMapper.getPasswordById(id);
    }

    @Override
    public Users findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public int remove(Long id) {
        return userMapper.remove(id);
    }

    @Override
    public int modifyPassword(Long id, String password) {
        return userMapper.modifyPassword(id, password);
    }

}
