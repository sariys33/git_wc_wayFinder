package com.wc.wayfinder.serivce;

import com.wc.wayfinder.dto.ManagerForm;
import com.wc.wayfinder.dto.ManagerResponse;

public interface ManagerService {

    // 회원가입
    void save(ManagerForm form);

    // 비밀번호 확인
    boolean checkPassword(Long id, String password);

    // 고유번호로 관리자정보 가져오기
    ManagerResponse findById(Long id);

    // 고유번호에 해당하는 관리자 삭제
    void remove(ManagerResponse user);

    // 비밀번호 변경
    int modifyPassword(ManagerForm user);

    // 이메일로 관리자 정보 가져오기
    ManagerResponse findByEmail(String email);

}
