package com.wc.wayfinder.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//로그인 실패시 실행되는 클래스  Spring Security 에서 로그인 시 인증이 실패할 때 호출되어 로그인 실패에 대한 커스텀 동작을 처리할 수 있게함
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("로그인 실패");
        log.info("request : {}", request);
        log.info("response : {}", response);
        // 로그인 실패 시 처리할 코드 작성
        // JavaScript 코드를 사용하여 알림창을 띄우도록 응답을 작성
        response.setContentType("text/html;charset=UTF-8");
        String referer = request.getHeader("referer");
        if(referer.contains("user")) {
            response.getWriter().write("<script>alert('아이디 또는 비밀번호가 일치 하지 않습니다.');  location.href='/user/login'; </script>");
        }
        if(referer.contains("manager")) {
            response.getWriter().write("<script>alert('아이디 또는 비밀번호가 일치 하지 않습니다.');  location.href='/manager/login'; </script>");
        }
        response.getWriter().flush();
    }
}



