package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Managers;
import lombok.Data;

@Data
public class ManagerForm {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Role role;

    // ManagerForm -> Manager(Entity)
    public Managers toEntity() {
        Managers managers = new Managers();
        managers.setEmail(this.email);
        managers.setPassword(this.password);
        managers.setNickname(this.nickname);
        managers.setRole(Role.MANAGER);
        return managers;
    }

}
