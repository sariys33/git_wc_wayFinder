package com.wc.wayfinder.security;

import com.wc.wayfinder.dto.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 로그인에 성공했을 시 실행
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 헤더에서 요청 이전에 url 가져오기
        String referer = request.getHeader("referer");
        log.info("referer : {}", referer);

        // 인증정보 꺼내서 담기
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        boolean roleCheck = false;

        // user 가 포함된 로그인 페이지에서 왔다면
        if (referer.contains("user")) {
            // 권한이 ROLE_USER 인지 확인
            roleCheck = userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.USER.getValue()));
            if(roleCheck) {
                // 맞다면 로그인 처리 후 메인으로 이동
                response.sendRedirect("/main");
            } else {
                // 아니라면 로그인 실패 처리
                handleLoginFailure(request, response);
            }
        } else if (referer.contains("manager")) {
            // manager 가 포함된 로그인 페이지에서 요청 했다면
            // 권한이 ROLE_MANAGER 인지 확인
            roleCheck = userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.MANAGER.getValue()));
            if(roleCheck) {
                // 맞다면 로그인 처리 후 메인으로 이동
                response.sendRedirect("/main");
            } else {
                // 아니라면 로그인 실패 처리
                handleLoginFailure(request, response);
            }
        }
    }

    // 로그인 실패 처리 시켜줄 메서드
    private void handleLoginFailure(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Login failure detected.");
        // 인증 정보 제거
        SecurityContextHolder.clearContext();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<script>alert('로그인에 실패했습니다.');  location.href='/user/login'; </script>");
        response.getWriter().flush();
    }

}
