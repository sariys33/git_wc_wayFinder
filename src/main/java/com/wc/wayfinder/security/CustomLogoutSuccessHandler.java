package com.wc.wayfinder.security;

import com.wc.wayfinder.dto.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그아웃 시 실행
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        boolean roleCheck = false;
        roleCheck = userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.MANAGER.getValue()));
        // 로그아웃하는 계정이 MANGER 권한 갖고 있다면
        if(roleCheck) {
            // 관리자 로그인 페이지로
            response.sendRedirect("/manager/login");
        } else {
            // 아니라면 유저 로그인 페이지로
            response.sendRedirect("/user/login");
        }
    }

}
