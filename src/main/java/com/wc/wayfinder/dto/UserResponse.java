package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponse {

    private Long id;
    private String email;
    private String password;
    private String nickname;

    public UserResponse(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
    }
}
