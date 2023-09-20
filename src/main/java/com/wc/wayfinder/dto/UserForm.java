package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Users;
import lombok.Data;

@Data
public class UserForm {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Role role;

    // UserForm -> Users(Entity)
    public Users toEntity() {
        Users user = new Users();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        user.setRole(Role.USER);
        return user;
    }

}
