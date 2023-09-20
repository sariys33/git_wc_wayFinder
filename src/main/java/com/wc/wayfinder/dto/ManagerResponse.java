package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Managers;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManagerResponse {

    private Long id;
    private String email;
    private String password;
    private String nickname;

    // Manager(Entity) -> ManagerResponse
    public ManagerResponse(Managers managers) {
        this.id = managers.getId();
        this.email = managers.getEmail();
        this.password = managers.getPassword();
        this.nickname = managers.getNickname();
    }

}
