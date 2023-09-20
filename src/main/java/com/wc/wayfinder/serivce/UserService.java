package com.wc.wayfinder.serivce;

import com.wc.wayfinder.dto.UserForm;
import com.wc.wayfinder.dto.UserResponse;

public interface UserService {

    // 회원가입
    void save(UserForm form);

    // 비밀번호 확인
    boolean checkPassword(Long id, String password);

    // 고유번호로 회원정보 가져오기
    UserResponse findById(Long id);

    // 고유번호에 해당하는 유저 삭제
    void remove(UserResponse user);

    // 비밀번호 변경
    int modifyPassword(UserForm user);

    // 이메일로 회원정보 가져오기
    UserResponse findByEmail(String email);

}
