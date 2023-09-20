package com.wc.wayfinder.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증되지 않은 상태로 권한 필요한 접근 할 시
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 알림창을 위한 스크립트 작성
        String alertScript = "alert('로그인이 필요한 서비스 입니다'); window.location.href = '/user/login';";

        // 알림창 출력 및 리다이렉트
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print("<script>" + alertScript + "</script>");
    }

}


