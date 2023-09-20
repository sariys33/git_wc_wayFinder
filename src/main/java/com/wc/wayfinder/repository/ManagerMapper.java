package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Managers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManagerMapper {

    // 회원 가입
    void save(Managers manager);

    // 이메일로 회원정보 가져오기
    Managers findByEmail(String email);

    // 고유번호로 비밀번호 가져오기
    String getPasswordById(Long id);

    // 고유번호로 회원정보 가져오기
    Managers findById(Long id);
    
    // 고유번호에 해당하는 회원 삭제
    void remove(Long id);
    
    // 비밀번호 변경
    int modifyPassword(@Param("id") Long id,
                       @Param("password") String password);
}
