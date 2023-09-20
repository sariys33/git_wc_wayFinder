package com.wc.wayfinder.domain;

import com.wc.wayfinder.dto.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Managers {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime regDate;
    private Role role;

}
