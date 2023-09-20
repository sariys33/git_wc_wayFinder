package com.wc.wayfinder.security;

import com.wc.wayfinder.domain.Managers;
import com.wc.wayfinder.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 권한 확인 해서 요청 시켜줄지 판단
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 세션에서 로그인 정보 가져오기
        Users user = (Users)request.getSession().getAttribute("user");
        Managers manager = (Managers)request.getSession().getAttribute("manager");

        // 요청 경로 가져오기
        String requestURI = request.getRequestURI();
        boolean checkURI = false;

        // 요청경로 체크
        boolean requestLogin = requestURI.contains("login");
        boolean requestSignup = requestURI.contains("signup");
        boolean requestForgotPw = requestURI.contains("forgotPw");

        // 로그인 상태일때 로그인/회원가입/비밀번호찾기 접근 금지
        if(user != null || manager != null) {
            if(requestLogin || requestSignup || requestForgotPw) {
                isAuthorized(request, response);
            }
        }

        // 사용자 로그인 상태
        if(user != null) {
            checkURI = requestURI.contains("manager");
            if(checkURI) {
                // 관리자 관련 경로 요청시 접근 금지
                handleRequestFailure(request, response);
            }
            // 아니라면 접근 허용
        } else if(manager != null) {
            // 관리자 로그인 상태
            checkURI = requestURI.contains("user");
            if(checkURI) {
                // 사용자 관련 경로 요청시 접근 금지
                handleRequestFailure(request, response);
            }
            // 아니라면 접근 허용
        }
    }

    // 요청 권한이 없을때 실행 시킬 메서드
    private void handleRequestFailure(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Request failure detected.");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<script>alert('요청 권한이 없습니다.'); window.history.go(-1);</script>");
    }

    // 로그인 상태일때 특정 url 요청하면 접근 거부할 메서드
    private void isAuthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("isAuthorized");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<script>alert('현재 로그인 상태 입니다.'); window.history.go(-1);</script>");
    }

}
