package com.wc.wayfinder.security.domain;

import com.wc.wayfinder.domain.Managers;
import com.wc.wayfinder.domain.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;

@Getter
public class CustomUser extends User {

    private Users user; // 우리가 사용하는 회원정보 담는 객체, 내부에 추가
    private Managers manager;

    public CustomUser(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }

    // 우리 버전의 생성자 -> 부모 생성자 호출하는 패턴은 유지
    public CustomUser(Users user) {
        super(user.getEmail(), user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(user.getRole().getValue())));
        this.user = user;
    }

    public CustomUser(Managers manager) {
        super(manager.getEmail(), manager.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(manager.getRole().getValue())));
        this.manager = manager;
    }
}
